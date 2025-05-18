package appEquip03.jocdelavida;

public class Principal {

	public static void main(String[] args) {
/*
 * Implementa el joc de la vida de Conway amb una matriu N x N de cadenes.
Inicialment hi ha una quantitat M (en posicions aleatòries) de cèl·lules (caselles)
vives. Cada 0,3 segons té lloc una transició entre l’estat actual i el següent i tenen
lloc certes accions en funció de l’estat de la cèl·lula i de les seues veïnes.
Les accions són les següents:
• Una cèl·lula viva que tinga menys de dues cèl·lules veïnes vives, mor.
• Una cèl·lula viva que tinga dues o tres cèl·lules veïnes vives, continua viva.
• Una cèl·lula viva que tinga més de tres cèl·lules veïnes vives, mor.
• Una cèl·lula morta que tinga tres cèl·lules veïnes vives, passa a estar viva.
Quan no queda cap cèl·lula viva el programa acaba. Si es produeixen més d’una
quantitat d’iteracions (generacions) determinada (per exemple 100), el joc també
 acaba, segurament ha arribat a un estat estacionari.
En cada iteració s’ha de mostrar la matriu, la generació i la quantitat de cèl·lules
vives que hi ha, així com les que s’han creat i han mort en total.
 */
		//crear cèl·lula
		//morir cèl·lula
		Vida VConway = new Vida();
		int i=0;
		boolean estancament = false;
		VConway.creacio(VConway.getvida(),VConway.getAlea(),VConway.getLIMIT_inici());

		try {
			while((VConway.recompteCel() != 0 ) && !estancament) {
				i++;
				estancament = VConway.preCicle(VConway.getvida());
				VConway.mostrarVida();
				VConway.Resum(i);
				VConway.cicleDeLaVida();
				Thread.sleep(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
