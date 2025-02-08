/*
    ClassicExplorer
    Author: bluecrab2
    Github: https://github.com/bluecrab2/ClassicExplorer
    Licence: All Rights Reserved

    ClassicExplorer is included with Save (mclm_save) with permission from bluecrab2.
*/

package com.mclegoman.level_converter.classicexplorer.fields;

import com.mclegoman.level_converter.classicexplorer.io.Reader;

import java.io.IOException;

/** Field for a boolean primitive */
public class BooleanField extends Field {
	boolean fieldValue;
	
	public BooleanField(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public Object getField() {
		return (Boolean) fieldValue;
	}
	
	@Override
	public void read() throws IOException {
		fieldValue = Reader.din.readBoolean();
	}
	
	@Override
	public BooleanField clone() {
		return new BooleanField(fieldName);
	}
}
