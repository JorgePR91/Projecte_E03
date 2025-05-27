package appEquip03.jocdelavida;

public enum Color {
	
	//Idea de l'Enum.values meua, taula amb els colors: 1er ia, després copia de Raül, resta de codi, ia.

    FONDO_NEGRO("\u001B[40m"),
    FONDO_ROJO("\u001B[41m"),
    FONDO_VERDE("\u001B[42m"),
    FONDO_AMARILLO("\u001B[43m"),
    FONDO_AZUL("\u001B[44m"),
    FONDO_MAGENTA("\u001B[45m"),
    FONDO_CIAN("\u001B[46m"),
    FONDO_BLANCO("\u001B[47m"),
    FONDO_GRIS("\u001B[100m"),
    RESET("\u001B[0m");
	
    private final String codigo;


	Color(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public static String aplicarColor(Color color, String texto) {
        return color.getCodigo() + texto + RESET.getCodigo();
    }
}
