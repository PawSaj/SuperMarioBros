package com.project.mario;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

import com.project.mario.entity.Coin;
import com.project.mario.entity.Entity;
import com.project.mario.entity.mob.Bowser;
import com.project.mario.entity.mob.Goomba;
import com.project.mario.entity.mob.Goomba2;
import com.project.mario.entity.mob.KoopaTroopa;
import com.project.mario.entity.mob.PiranhaPlant;
import com.project.mario.entity.mob.Player;
import com.project.mario.enums.Facing;
import com.project.mario.enums.Id;
import com.project.mario.enums.PlayerStates;
import com.project.mario.enums.TypeOfPipe;
import com.project.mario.enviroment.BonusBlock;
import com.project.mario.enviroment.Castel;
import com.project.mario.enviroment.EnviromentObject;
import com.project.mario.enviroment.FireBlock;
import com.project.mario.enviroment.Flag;
import com.project.mario.enviroment.Ground;
import com.project.mario.enviroment.Hammer;
import com.project.mario.enviroment.Pipe;
import com.project.mario.enviroment.Princes;
import com.project.mario.enviroment.TeleportPlace;
import com.project.mario.enviroment.Wall;

/**
 * Klasa odpowiedzialna za stworzenie i wyrenderowanie wszystkich obiektów gry.
 * W czasie dzia³ania gry wywo³uje kolejny krok dla ka¿dego elementu
 * 
 * @author Pawe³ Sajnóg
 *
 */
public class Handler {
	private GameLogic gameLogic;
	public CopyOnWriteArrayList<Entity> entity;
	public CopyOnWriteArrayList<EnviromentObject> enviromentObject;

	public Handler(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
		this.entity = new CopyOnWriteArrayList<Entity>();
		this.enviromentObject = new CopyOnWriteArrayList<EnviromentObject>();
	}

	/**
	 * Metoda renderuj¹ca wszystkie elementy gry. S¹ one renderowane fazowo w
	 * kolejnoœci jakiej maj¹ siê wyœwietlaæ i ew. nachodziæ na siebie (który
	 * jest wtedy widoczny). Na pierwszym planie s¹ "fireballe" strzelane przez
	 * mario, potem s¹ elementy takie jak zniszczony blok, moneta wyskakuj¹ca z
	 * bloku oraz martwy gracz. Nastêpnie takie rzeczy jak ruroci¹gi,
	 * ksiê¿niczka m³ot spustowy mostu zabijaj¹cego Bowsera, ziemia, dalej jest
	 * wyœwietlany gracz, potem na kolejnym planie s¹ mniej w¹zne elementy jak
	 * flaga, zamek, czy pnie (podstawy drzew z 3 poziomu). Na samym koñcy s¹
	 * zwykli przeciwnicy, monety, p³omienie Bowsera.
	 * 
	 * @param g
	 *            przekazany parametr grafiki danego okna
	 */
	public void render(Graphics g) {
		for (Entity e : entity) {
			if (gameLogic.getVisibleField() != null && e.getBounds().intersects(gameLogic.getVisibleField()))
				if (e.getId() != Id.player && e.getId() != Id.crashedBlock && e.getState() != PlayerStates.dead
						&& e.getId() != Id.coinFromBlock)
					e.render(g);
		}

		for (EnviromentObject env : enviromentObject) {
			if (gameLogic.getVisibleField() != null && env.getBounds().intersects(gameLogic.getVisibleField()))
				if (env.getId() == Id.flag || env.getId() == Id.castel || env.getId() == Id.trunk)
					env.render(g);
		}

		for (Entity e : entity) {
			if (gameLogic.getVisibleField() != null && e.getBounds().intersects(gameLogic.getVisibleField()))
				if (e.getId() == Id.player && e.getState() != PlayerStates.dead && e.getId() != Id.coinFromBlock)
					e.render(g);
		}

		for (EnviromentObject env : enviromentObject) {
			if (gameLogic.getVisibleField() != null && env.getBounds().intersects(gameLogic.getVisibleField()))
				if (env.getId() != Id.flag && env.getId() != Id.castel && env.getId() != Id.piranhaPlant
						&& env.getId() != Id.fireball && env.getId() != Id.trunk)
					env.render(g);
		}

		for (Entity e : entity) {
			if (gameLogic.getVisibleField() != null && e.getBounds().intersects(gameLogic.getVisibleField()))
				if (e.getState() == PlayerStates.dead || e.getId() == Id.crashedBlock || e.getId() == Id.coinFromBlock)
					e.render(g);
		}
		for (EnviromentObject env : enviromentObject) {
			if (gameLogic.getVisibleField() != null && env.getBounds().intersects(gameLogic.getVisibleField()))
				if (env.getId() == Id.fireball && env.getId() != Id.trunk)
					env.render(g);
		}
	}

	/**
	 * Metoda wywo³uj¹ca update dla wszystkich elementów gry
	 */
	public void update() {
		for (Entity e : entity) {
			e.update();
		}

		for (EnviromentObject eo : enviromentObject) {
			eo.update();
		}
	}

	/**
	 * Metoda dodaj¹ca element do listy obiektów {@link Entity}
	 * 
	 * @param en
	 *            element, który chcemy dodaæ
	 */
	public void addEntity(Entity en) {
		entity.add(en);
	}

	/**
	 * Metoda usuwaj¹ca element z listy obiektów {@link Entity}
	 * 
	 * @param en
	 *            element, który chcemy usun¹æ
	 */
	public void removeEntity(Entity en) {
		entity.remove(en);
	}

	/**
	 * Metoda dodaj¹ca element do listy obiektów {@link EnviromentObject}
	 * 
	 * @param en
	 *            element, który chcemy dodaæ
	 */
	public void addEnviromentObject(EnviromentObject en) {
		enviromentObject.add(en);
	}

	/**
	 * Metoda usuwaj¹ca element z listy obiektów {@link EnviromentObject}
	 * 
	 * @param en
	 *            element, który chcemy usun¹æ
	 */
	public void removeEnviromentObject(EnviromentObject en) {
		enviromentObject.remove(en);
	}

	/**
	 * Metoda odczytuj¹ca kolejne piksele pliku graficznego zawieraj¹ca dany
	 * poziom gry. Dla zadanych wartoœci generowane s¹ odpowiednie elementy.
	 * 
	 * @param level
	 *            obraz poziomu
	 * 
	 */

	public void createLevel(BufferedImage level) {
		int width = level.getWidth();
		int height = level.getHeight();

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = level.getRGB(x, y);

				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;

				if (red == 0 && green == 0 && blue == 0)
					addEnviromentObject(new Ground(x * 64, y * 64, 64, 64, true, Id.ground, gameLogic));
				if (red == 0 && green == 0 && blue == 10)
					addEnviromentObject(new Ground(x * 64, y * 64, 64, 64, true, Id.groundTreeLeft, gameLogic));
				if (red == 0 && green == 0 && blue == 20)
					addEnviromentObject(new Ground(x * 64, y * 64, 64, 64, true, Id.groundTreeMiddle, gameLogic));
				if (red == 0 && green == 0 && blue == 30)
					addEnviromentObject(new Ground(x * 64, y * 64, 64, 64, true, Id.groundTreeRight, gameLogic));
				if (red == 0 && green == 0 && blue == 40)
					addEnviromentObject(new Ground(x * 64, y * 64, 64, 64, true, Id.bowserCastelGround, gameLogic));
				if (red == 0 && green == 0 && blue == 50)
					addEnviromentObject(new Ground(x * 64, y * 64, 64, 64, true, Id.bowserCastelBridge, gameLogic));
				if (red == 0 && green == 10 && blue == 0)
					addEnviromentObject(new Wall(x * 64, y * 64, 64, 64, Id.wall, gameLogic));
				if (red == 0 && green == 20 && blue == 0)
					addEnviromentObject(new Wall(x * 64, y * 64, 64, 64, Id.hardWall, gameLogic));
				if (red == 0 && green == 30 && blue == 0)
					addEnviromentObject(new Wall(x * 64, y * 64, 64, 64, Id.trunk, gameLogic));
				if (red == 90 && green == 90 && blue == 90)
					addEnviromentObject(new Hammer(x * 64, y * 64, 64, 64, Id.hammer, gameLogic));
				if (red == 70 && green == 70 && blue == 70)
					addEnviromentObject(new FireBlock(x * 64, y * 64, 64, 64, Id.fireBlock, gameLogic));
				if (red == 80 && green == 80 && blue == 80)
					addEnviromentObject(new Princes(x * 64, y * 64 - 32, 64, 96, Id.princes, gameLogic));
				if (red == 255 && green == 255 && blue == 0)
					addEnviromentObject(new BonusBlock(x * 64, y * 64, 64, 64, Id.powerUpBlock, gameLogic));
				if (red == 250 && green == 250 && blue == 0)
					addEnviromentObject(new BonusBlock(x * 64, y * 64, 64, 64, Id.starmanBlock, gameLogic));
				if (red == 245 && green == 245 && blue == 0)
					addEnviromentObject(new BonusBlock(x * 64, y * 64, 64, 64, Id.coinBlock, gameLogic));
				if (red == 240 && green == 240 && blue == 0)
					addEnviromentObject(new BonusBlock(x * 64, y * 64, 64, 64, Id.hiddenCoinsBlock, gameLogic));
				if (red == 0 && green == 255 && blue == 0)
					addEntity(new Goomba(x * 64, y * 64, 64, 64, Id.goomba, gameLogic));
				//--dodatkowy element w ramach zadania
				if (red == 1 && green == 1 && blue == 1)
					addEntity(new Goomba2(x * 64, y * 64, 64, 64, Id.goomba, gameLogic));
				/////
				if (red == 0 && green == 255 && blue == 255)
					addEntity(new KoopaTroopa(x * 64, y * 64 - 32, 64, 96, Id.koopaTroopa, gameLogic, Facing.left));
				if (red == 0 && (green >= 122 && green <= 129) && blue == 0) {
					if (green == 129)
						addEnviromentObject(
								new Pipe(x * 64, y * 64, 128, 64, Id.pipe, gameLogic, TypeOfPipe.verticalOpenEntrance));
					else if (green == 128)
						addEnviromentObject(
								new Pipe(x * 64, y * 64, 128, 64, Id.pipe, gameLogic, TypeOfPipe.verticalClosedOutput));
					else if (green == 127)
						addEnviromentObject(
								new Pipe(x * 64, y * 64, 128, 64, Id.pipe, gameLogic, TypeOfPipe.verticalPieceOfPipe));
					else if (green == 126)
						addEnviromentObject(new Pipe(x * 64, y * 64, 128, 64, Id.pipe, gameLogic,
								TypeOfPipe.verticalTopOfConnector));
					else if (green == 125)
						addEnviromentObject(new Pipe(x * 64, y * 64, 128, 64, Id.pipe, gameLogic,
								TypeOfPipe.verticalBottomOfConnector));
					else if (green == 124)
						addEnviromentObject(
								new Pipe(x * 64, y * 64, 64, 128, Id.pipe, gameLogic, TypeOfPipe.horizontalEntrance));
					else if (green == 123)
						addEnviromentObject(new Pipe(x * 64, y * 64, 64, 128, Id.pipe, gameLogic,
								TypeOfPipe.horizontalPieceOfPipe));
					else if (green == 122)
						addEnviromentObject(
								new Pipe(x * 64, y * 64, 128, 64, Id.pipe, gameLogic, TypeOfPipe.verticalOpenOutput));
				}
				if (red == 140 && green == 250 && blue == 200)
					addEntity(new PiranhaPlant(x * 64 - 32, y * 64, 64, 96, Id.piranhaPlant, gameLogic));
				if (red == 255 && green == 250 && blue == 0)
					addEntity(new Coin(x * 64, y * 64, 64, 64, Id.coin, gameLogic, false));
				if (red == 127 && green == 0 && blue == 127)
					addEntity(new Bowser(x * 64, y * 64, 128, 128, Id.bowser, gameLogic));
				if (red == 255 && green == 8 && blue == 127)
					addEnviromentObject(new Flag(x * 64, y * 64, 64, 10 * 64, Id.flag, gameLogic));
				if (red == 100 && green == 100 && blue == 100)
					addEnviromentObject(new Castel(x * 64, y * 64, 6 * 64, 6 * 64, Id.castel, gameLogic));
				if (red == 0 && green == 0 && blue == 255)
					addEntity(new Player(x * 64, y * 64, 64, 64, Id.player, gameLogic, Facing.right));
				if (red == 150 && green == 150 && blue == 150) {
					addEnviromentObject(new TeleportPlace(x * 64, y * 64, 64, 64, Id.teleportPlace, gameLogic));
				}
			}
		}
	}

	/**
	 * Metoda czyszcz¹ca poziom, usuwaj¹ca wszystkie elementy z list elementów
	 * typu {@link EnviromentObject} i {@link Entity}
	 */
	public void clearLevel() {
		entity.clear();
		enviromentObject.clear();
	}
}
