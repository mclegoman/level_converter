/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.convert;

import com.mclegoman.level_converter.classicexplorer.fields.ArrayField;
import com.mclegoman.level_converter.classicexplorer.fields.BlocksField;
import com.mclegoman.level_converter.classicexplorer.fields.ClassField;
import com.mclegoman.level_converter.classicexplorer.fields.Field;
import com.mclegoman.level_converter.classicexplorer.io.Reader;
import com.mclegoman.level_converter.exception.ConvertFailException;
import com.mclegoman.level_converter.nbt.*;
import com.mclegoman.level_converter.util.Formats;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Convert {
	public static void convert(Data data, FinishConvert onFinished) {
		if (data.outType.equals(Formats.indev)) convertToIndev(data, onFinished);
		else if (data.outType.equals(Formats.infdev)) convertToInfdev(data, onFinished);
	}
	private static void convertToIndev(Data data, FinishConvert onFinished) {
		if (data.inType.equals(Formats.classic)) convertClassicToIndev(data, onFinished);
		else onFinished.run(getInvalidTypeMessage(), JOptionPane.ERROR_MESSAGE);
	}
	private static void convertClassicToIndev(Data data, FinishConvert onFinished) {
		onFinished.run("Successfully converted Classic level to Indev!", JOptionPane.INFORMATION_MESSAGE);
	}
	private static void convertToInfdev(Data data, FinishConvert onFinished) {
		if (data.inType.equals(Formats.classic)) convertClassicToInfdev(data, onFinished);
		else if (data.inType.equals(Formats.indev)) convertIndevToInfdev(data, onFinished);
		else onFinished.run(getInvalidTypeMessage(), JOptionPane.ERROR_MESSAGE);
	}
	private static void convertClassicToInfdev(Data config, FinishConvert onFinished) {
		try {
			long seed = new Random().nextLong();
			int spawnX = 128;//SaveConfig.instance.conversionSettings.spawnX.value();
			int spawnY = 36;//SaveConfig.instance.conversionSettings.spawnY.value();
			int spawnZ = 128;//SaveConfig.instance.conversionSettings.spawnZ.value();
			int time = 0;//SaveConfig.instance.conversionSettings.time.value();
			byte[] blocks = null;
			ClassField blockMap = null;
			short height = 64;//SaveConfig.instance.conversionSettings.height.value().shortValue();
			short length = 256;//SaveConfig.instance.conversionSettings.length.value().shortValue();
			short width = 256;//SaveConfig.instance.conversionSettings.width.value().shortValue();
			final NbtCompound[] playerData = new NbtCompound[]{null};
			for (Field field : Reader.read(config.input).getFields()) {
				if (field.getFieldName().equals("createTime")) {
					seed = (long) field.getField();
				} else if (field.getFieldName().equals("xSpawn")) {
					spawnX = (int) field.getField();
				} else if (field.getFieldName().equals("ySpawn")) {
					spawnY = (int) field.getField();
				} else if (field.getFieldName().equals("zSpawn")) {
					spawnZ = (int) field.getField();
				} else if (field.getFieldName().equals("tickCount")) {
					time = (int) field.getField();
				} else if (field.getFieldName().equals("blocks")) {
					blocks = ((BlocksField)field).getBlocks();
				} else if (field.getFieldName().equals("blockMap")) {
					blockMap = ((ClassField) field);
				} else if (field.getFieldName().equals("width")) {
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					width = Short.parseShort(String.valueOf(field.getField()));
				} else if (field.getFieldName().equals("height")) {
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					length = Short.parseShort(String.valueOf(field.getField())); // Was changed from "height" to "length" in Indev.
				} else if (field.getFieldName().equals("depth")) {
					// We get the short value of the stringified value as it could either be a short or an int, depending on the version it was saved in.
					height = Short.parseShort(String.valueOf(field.getField())); // Was changed from "depth" to "height" in Indev.
				}
			}
			if (blocks == null) throw new ConvertFailException("No blocks found!");
			else {
				if (blocks.length == (width * height * length)) {
					if (config.convertPlayerData) {
						if (blockMap != null) {
							blockMap.getClassField().getFields().forEach((field) -> {
								if (field.getFieldName().equals("all")) {
									((ClassField)field).getArrayList().forEach(entityData -> {
										if (entityData.getName().equals("com.mojang.minecraft.player.Player")) {
											NbtCompound data = new NbtCompound();
											data.putString("id", "LocalPlayer");
											entityData.getFields().forEach(player -> {
												if (player.getFieldName().equals("inventory")) {
													NbtList inventory = new NbtList();
													ArrayList<Field> inventory1 = ((ClassField)player).getClassField().getFields();
													inventory1.forEach(invField -> {
														if (invField.getFieldName().equals("count")) {
															for (Field field2 : ((ArrayField)invField).getArray()) {
																NbtCompound itemData = new NbtCompound();
																byte count = ((Integer)field2.getField()).byteValue();
																itemData.putByte("Count", count);
																if (count != 0) inventory.add(itemData);
															}
														} else if (invField.getFieldName().equals("slots")) {
															int index = 0;
															int slot = 0;
															for (Field field2 : ((ArrayField)invField).getArray()) {
																short id = ((Integer)field2.getField()).shortValue();
																if (id >= 0) {
																	NbtCompound itemData = (NbtCompound) inventory.get(index);
																	itemData.putShort("id", id);
																	itemData.putByte("Slot", ((Integer)slot).byteValue());
																	index++;
																}
																slot++;
															}
														}
													});
													data.put("Inventory", inventory);
												}
												if (player.getFieldName().equals("score")) {
													data.putInt("Score", (int) player.getField());
												}
											});
											NbtList motion = toNbtList(0.0D, 0.0D, 0.0D);
											NbtList pos = new NbtList();
											NbtList rotation = toNbtList(0.0F, 0.0F);
											entityData.getSuperClass().getSuperClass().getFields().forEach(entity -> {
												if (entity.getFieldName().equals("x") || entity.getFieldName().equals("y") || entity.getFieldName().equals("z")) {
													pos.add(new NbtDouble((float) entity.getField()));
												}
												if (entity.getFieldName().equals("fallDistance")) {
													data.putFloat("FallDistance", (float) entity.getField());
												}
											});
											data.put("Motion", motion);
											data.put("Pos", pos);
											data.put("Rotation", rotation);
											data.putShort("Air", (short) 300);
											data.putShort("AttackTime", (short) 0);
											data.putShort("DeathTime", (short) 0);
											data.putShort("HurtTime", (short) 0);
											data.putShort("Health", (short) 20);
											data.putShort("Fire", (short) -20);
											playerData[0] = data;
										}
									});
								}
							});
						}
					}
				} else throw new ConvertFailException("Invalid block amount!");
				int maxYOffset = 128 - height;
				//if (maxYOffset > 0) minecraft.m_6408915(new SliderConfirmScreen(new ConvertWorldInfoScreen(parent, "Setting y offset...", worldName, input, width, length, height, null, playerData[0], new WorldData(blocks, time, seed, (short) spawnX, (short) spawnY, (short) spawnZ)), "Do you want to offset your world vertically?", "Select how many blocks upwards you want to shift your world", 0, "Y Offset", maxYOffset, "Confirm"));

				File output = new File(config.output.toFile(), config.input.getName());
				convertBlocksToInfdev(config, output, width, height, length, blocks, null, time, 0);
				createInfdevLevel(config, output, seed, spawnX, spawnY + 0, spawnZ, time, calculateSizeOnDisk(output, width, length), playerData[0]);
				//convertClassicFinish(minecraft, parent, worldName, width, height, length, blocks, playerData[0], time, seed, (short) spawnX, (short) spawnY, (short) spawnZ, 0);
				onFinished.run("Successfully converted Classic level to Infdev!", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception error) {
			onFinished.run("Failed to convert Classic level to Infdev: " + error.getLocalizedMessage(), JOptionPane.WARNING_MESSAGE);
		}
	}
	private static long calculateSizeOnDisk(final File dir, final short width, final short length) {
		long sizeOnDisk = 0L;
		int total = ((width / 16) * (length / 16));
		for (int chunk = 0; chunk < total; chunk++) sizeOnDisk += getChunkFile(dir, chunk % (width / 16), chunk / (width / 16)).length();
		return sizeOnDisk;
	}
	private static void convertIndevToInfdev(Data data, FinishConvert onFinished) {
		onFinished.run("Successfully converted Indev level to Infdev!", JOptionPane.INFORMATION_MESSAGE);
	}
	private static String getInvalidTypeMessage() {
		return "Input format cannot be converted to Output format.";
	}
	public static NbtList toNbtList(float... fs) {
		NbtList nbtList = new NbtList();
		for (float value : fs) nbtList.add(new NbtFloat(value));
		return nbtList;
	}
	public static NbtList toNbtList(double... ds) {
		NbtList nbtList = new NbtList();
		for (double value : ds) nbtList.add(new NbtDouble(value));
		return nbtList;
	}
	private static void convertBlocksToInfdev(Data config, final File dir, final short width, final short height, final short length, final byte[] blocks, final byte[] blocksData, final long ticks, final int yOffset) throws ConvertFailException, IOException {
		// inf-20100227 changed the world height from 256, to 127.
		// https://minecraft.wiki/w/Java_Edition_Infdev_20100227-1414
		if (width % 16 != 0) throw new ConvertFailException("Width was " + width + ", expecting value divisible by 16!");
		if (height <= 0 || height > 127) throw new ConvertFailException("Height was " + height + ", expecting value between 1 and 127!");
		if (length % 16 != 0) throw new ConvertFailException("Length was " + length + ", expecting value divisible by 16!");
		if (blocksData == null) System.out.println("No block data present: Block light and metadata will be set to default, you may encounter lag when these update for the first time.");
		if (blocks.length == width * height * length) {
			int total = ((width / 16) * (length / 16));
			for (int chunk = 0; chunk < total; chunk++) {
				int x = chunk % (width / 16);
				int z = chunk / (width / 16);
				File chunkFile = getChunkFile(dir, x, z);
				NbtCompound chunkData = new NbtCompound();
				NbtCompound level = new NbtCompound();
				level.putInt("xPos", x);
				level.putInt("zPos", z);
				level.putLong("LastUpdate", ticks);
				level.putByteArray("Blocks", getBlocksForChunk(config, x, z, width, height, length, blocks, yOffset));
				level.putByteArray("Data", blocksData != null ? getBlockDataForInfdevChunk(x, z, width, height, length, blocksData, yOffset, false) : new byte[16 * 16 * 64]);
				level.putByteArray("SkyLight", new byte[16 * 16 * 128]);
				level.putByteArray("BlockLight", blocksData != null ? getBlockDataForInfdevChunk(x, z, width, height, length, blocksData, yOffset, true) : new byte[16 * 16 * 64]);
				level.putByteArray("HeightMap", calcInfdevHeightMap());
				level.put("TileEntities", new NbtList());
				chunkData.put("Level", level);
				save(chunkData, Files.newOutputStream(chunkFile.toPath()));
			}
		} else throw new ConvertFailException("Invalid block amount!");
	}
	private static byte[] calcInfdevHeightMap() {
		// It's not perfect (e.g transparent blocks probably wouldn't be counted), but the game should fix this when saving anyway.
		byte[] heightMap = new byte[16 * 16];
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				for (int y = 0; y < 128; y++) {
					int currentHeight = 127 - y;
					heightMap[x * 16 + z] = (byte) (currentHeight + 1);
				}
			}
		}
		return heightMap;
	}
	private static byte[] getBlockDataForInfdevChunk(final int x, final int z, final int width, final short height, final int length, final byte[] blockData, final int yOffset, final boolean isLight) {
		byte[] output = new byte[16 * 16 * 64];
		int index = 0;
		for (int xIndex = x * 16; xIndex < x * 16 + 16; xIndex++) {
			for (int zIndex = z * 16; zIndex < z * 16 + 16; zIndex++) {
				for (int y = 0; y < yOffset; y += 2) {
					index += 1;
				}
				for (int yIndex = 0; yIndex < height; yIndex += 2) {
					byte a = blockData[(yIndex * length + zIndex) * width + xIndex];
					byte b = blockData[((yIndex + 1) * length + zIndex) * width + xIndex];
					if (isLight) {
						byte lightByte = (byte) ((a & 15) * 16 + b & 15);
						if (lightByte > 127) lightByte -= (byte) 256;
						output[index] = lightByte;
					} else {
						byte dataByte = (byte) ((a >> 4) * 16 + b >> 4);
						if (dataByte > 127) dataByte -= (byte) 256;
						output[index] = dataByte;
					}
					index += 1;
				}
				index += ((128 - height - yOffset) / 2);
			}
		}
		return output;
	}
	private static void createInfdevLevel(Data config, final File dir, final long seed, final int spawnX, final int spawnY, final int spawnZ, final long time, final long sizeOnDisk, final NbtCompound player) throws IOException {
		dir.mkdirs();
		File level = new File(dir, "level.dat");
		NbtCompound data = new NbtCompound();
		data.putLong("RandomSeed", seed);
		data.putInt("SpawnX", spawnX);
		data.putInt("SpawnY", spawnY);
		data.putInt("SpawnZ", spawnZ);
		data.putLong("Time", time);
		data.putLong("SizeOnDisk", sizeOnDisk);
		data.putLong("LastPlayed", System.currentTimeMillis());
		if (player != null) data.putCompound("Player", player);
		NbtCompound output = new NbtCompound();
		output.put("Data", data);
		save(output, Files.newOutputStream(level.toPath()));
	}
	public static File getChunkFile(File file, int x, int z) {
		int convertedX = convertChunkCoord(x, true);
		int convertedY = convertChunkCoord(z, true);
		File folder = new File(new File(file, toBase36(convertedX, true)), toBase36(convertedY, true));
		folder.mkdirs();
		return new File(folder, "c." + ((convertedX < 0) ? "-" : "") + toBase36((convertedX < 0) ? (convertedX * -1) : convertedX, false) + "."  + ((convertedY < 0) ? "-" : "") + toBase36((convertedY < 0) ? (convertedY * -1) : convertedY, false) + ".dat");
	}
	public static int convertChunkCoord(int coord, boolean actual) {
		if (actual) {
			// When actual is set to true, convert values that are meant to be negative to negative.
			if (coord > 134217727) return (-268435455 + coord) - 1;
		} else {
			// When actual is set to false, convert negative values to the values the game uses in place of negatives.
			if (coord < 0) return 268435456 - (coord * -1);
		}
		return coord;
	}
	public static String toBase36(int i, boolean folder) {
		int value = getUnsignedValue((byte) i);
		return folder ? Integer.toString(value % 64, 36) : (i < 0 ? "-" : "") + Integer.toString(value, 36);
	}
	public static int getUnsignedValue(byte value) {
		return value & 0xFF;
	}
	private static byte[] getBlocksForChunk(Data config, final int x, final int z, final int width, final short height, final int length, final byte[] blocks, final int yOffset) {
		byte[] chunk = new byte[16 * 16 * 128];
		int index = 0;
		for (int xChunk = 0; xChunk < 16; xChunk++) {
			for (int zChunk = 0; zChunk < 16; zChunk++) {
				for (int y = 0; y < yOffset; y++) {
					chunk[index] = 0;//SaveConfig.instance.conversionSettings.offsetBlockId.value().byteValue();
					index += 1;
				}
				for (int y = 0; y < height; y++) {
					byte block = blocks[(((y * length + (z * 16 + zChunk)) * width) + (x * 16 + xChunk))];
					if (/*SaveConfig.instance.conversionSettings.replaceBedrock.value() &&*/ block == (byte) 7) block = 0;//SaveConfig.instance.conversionSettings.offsetBlockId.value().byteValue();
					chunk[index] = block;
					index++;
				}
				index += (128 - height - yOffset);
			}
		}
		return chunk;
	}
	public static class Data {
		private final Formats inType;
		private final Formats outType;
		private final File input;
		private final Path output;
		private final boolean convertPlayerData;
		public Data(Formats inType, Formats outType, File input, Path output, boolean convertPlayerData) {
			this.inType = inType;
			this.outType = outType;
			this.input = input;
			this.output = output;
			this.convertPlayerData = convertPlayerData;
		}
	}
	public interface FinishConvert {
		void run(String message, int messageType);
	}
	public static NbtCompound load(InputStream inputStream) throws IOException {
		DataInputStream dataInputStream = new DataInputStream(new GZIPInputStream(inputStream));
		NbtCompound var5;
		try {
			NbtElement var1;
			if (!((var1 = NbtElement.deserialize(dataInputStream)) instanceof NbtCompound)) throw new IOException("Root tag must be a named compound tag");
			var5 = (NbtCompound)var1;
		} finally {
			inputStream.close();
		}
		return var5;
	}
	public static void save(NbtCompound nbtCompound, OutputStream outputStream) throws IOException {
		try (DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(outputStream))) {
			NbtElement.serialize(nbtCompound, dataOutputStream);
		}
	}
}