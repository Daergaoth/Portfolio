package com.daergaoth.videoplayer;

import com.daergaoth.videoplayer.constants.ApplicationConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

@SpringBootApplication
public class VideoPlayerApplication {

	public static void main(String[] args) {
		if(args.length > 0){
			ApplicationConstants.VIDEO_ROOT_FOLDER = args[0];
		}
		ConfigurableApplicationContext cac = SpringApplication.run(VideoPlayerApplication.class, args);
		cac.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {

			@Override
			public void onApplicationEvent(ContextClosedEvent event) {
				System.out.println("Do something");
			}
		});
	}

}
