/*
    Level Converter
    Contributor(s): dannytaylor
    Github: https://github.com/mclegoman/level_converter
    Licence: GNU LGPLv3
*/

package com.mclegoman.level_converter.convert;

public class ConvertThread extends Thread {
	private final Convert.Data data;
	private final Convert.FinishConvert onFinished;
	public ConvertThread(Convert.Data data, Convert.FinishConvert onFinished) {
		this.data = data;
		this.onFinished = onFinished;
	}
	public void run() {
		Convert.convert(this.data, this.onFinished);
		this.interrupt();
	}
}
