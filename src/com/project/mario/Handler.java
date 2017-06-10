package com.project.mario;

import java.awt.Graphics;
import java.util.concurrent.CopyOnWriteArrayList;

import com.project.mario.entity.Entity;
import com.project.mario.enviroment.EnviromentObject;

public class Handler {
	public CopyOnWriteArrayList<Entity> entity;
	public CopyOnWriteArrayList<EnviromentObject> enviromentObject;

	public Handler() {
		this.entity = new CopyOnWriteArrayList<Entity>();
		this.enviromentObject = new CopyOnWriteArrayList<EnviromentObject>();
	}

	public void render(Graphics g) {
		for(Entity e:entity){
			e.render(g);
		}
		
		for(EnviromentObject eo:enviromentObject){
			eo.render(g);
		}
	}

	public void update() {
		for(Entity e:entity){
			e.update();
		}
		
		for(EnviromentObject eo:enviromentObject){
			eo.update();
		}
	}
	
	public void addEntity(Entity en) {
		entity.add(en);
	}
	
	public void removeEntity(Entity en) {
		entity.remove(en);
	}
	
	public void addEnviromentObject(EnviromentObject enO){
		enviromentObject.add(enO);
	}
	
	public void removeEnviromentObject(EnviromentObject enO){
		enviromentObject.remove(enO);
	}
}
