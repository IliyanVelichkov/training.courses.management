package training.courses.management.system.commons.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public enum GsonFactory {

	INSTANCE;

	private final Gson gson;

	private GsonFactory() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new DateTimeAdapter());
		gsonBuilder.setPrettyPrinting();
		this.gson = gsonBuilder.create();
	}

	public Gson getGson() {
		return this.gson;
	}

}
