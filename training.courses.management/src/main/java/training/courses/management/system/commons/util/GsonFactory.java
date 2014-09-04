package training.courses.management.system.commons.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public enum GsonFactory {

	INSTANCE;

	public Gson createGson() {
		GsonBuilder gsonBuilder = createSimpleBuilder();
		return gsonBuilder.create();
	}

	public Gson createExclusiveGson() {
		GsonBuilder gsonBuilder = createSimpleBuilder();
		gsonBuilder.excludeFieldsWithoutExposeAnnotation();
		return gsonBuilder.create();
	}

	private GsonBuilder createSimpleBuilder() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTimeAdapter());
		gsonBuilder.setPrettyPrinting();
		return gsonBuilder;
	}
}
