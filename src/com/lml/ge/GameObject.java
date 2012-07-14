package com.lml.ge;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import lombok.Data;

import org.jboss.netty.buffer.ChannelBuffer;

import com.lml.ge.helper.Util;

@Data
public abstract class GameObject {
	public static final Map<String, Field> fieldMap = Util.getFieldMap(GameObject.class);
	
	public int id;
	public String name;
	public byte [] nameBytes;
	
	protected Field getField(String fieldName) { 
		return fieldMap.get(fieldName);
	}
	
	protected void writeTo(ChannelBuffer buffer) {
		buffer.writeInt(id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Arrays.hashCode(nameBytes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameObject other = (GameObject) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (!Arrays.equals(nameBytes, other.nameBytes))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameObject [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append("]");
		return builder.toString();
	}
	
	abstract protected void setAttirbute(String fieldName, String value);
}
