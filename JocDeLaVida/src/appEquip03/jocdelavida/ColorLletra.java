package appEquip03.jocdelavida;

public enum ColorLletra {
	
    NEGRO("\u001B[30m"),
    ROJO("\u001B[31m"),
    VERDE("\u001B[32m"),
    AMARILLO("\u001B[33m"),
    AZUL("\u001B[34m"),
    MAGENTA("\u001B[35m"),
    CIAN("\u001B[36m"),
    BLANCO("\u001B[37m"),
    RESET("\u001B[0m");
    
    private final String codigo;


	ColorLletra(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public static String aplicarColor(Color color, String texto) {
        return color.getCodigo() + texto + RESET.getCodigo();
    }
}
