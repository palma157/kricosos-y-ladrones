package laberinto;

import java.awt.Color;
import java.awt.Graphics;
import personajes.Jugador;

// Clase Pocion representa una poción en el juego que el jugador puede usar para recuperar salud.
public class Pocion {
    // Atributos de la poción
    private int x;          // Coordenada X de la poción en el mapa
    private int y;          // Coordenada Y de la poción en el mapa
    private String nombre;  // Nombre de la poción
    private boolean usada;  // Indica si la poción ha sido usada o no

    /*
     * Constructor de la clase Pocion.
     * Inicializa las coordenadas de la poción, su nombre y su estado (no usada inicialmente).
     * @param x Coordenada X de la poción
     * @param y Coordenada Y de la poción
     * @param nombre Nombre de la poción
     */
    public Pocion(int x, int y, String nombre) {
        this.x = x;
        this.y = y;
        this.nombre = nombre;
        this.usada = false;  // Inicialmente la poción no ha sido usada
    }

    /*
     * Método paint.
     * Dibuja la poción en el mapa si aún no ha sido usada.
     * @param g Objeto Graphics para dibujar en la pantalla.
     */
    public void paint(Graphics g) {
        if (!usada) {  // Solo se dibuja si no ha sido usada
            g.setColor(Color.green);  // Color de la poción
            g.fillOval(x, y, 20, 20); // Dibujar la poción como un óvalo verde
        }
    }

    /*
     * Método usar.
     * Permite al jugador usar la poción, recuperando su salud máxima y marcando la poción como usada.
     * @param jugador El jugador que usa la poción.
     */
    public void usar(Jugador jugador) {
        if (!usada) {  // Solo puede usarse si no ha sido usada previamente
            System.out.println("Has usado una " + getNombre() + " y has recuperado tu salud.");
            jugador.restablecerSaludMaxima();  // El jugador recupera toda su salud
            usada = true;  // Marca la poción como usada
        }
    }

    /*
     * Método getX.
     * Devuelve la coordenada X de la poción en el mapa.
     * @return Coordenada X de la poción.
     */
    public int getX() {
        return x;
    }

    /*
     * Método getY.
     * Devuelve la coordenada Y de la poción en el mapa.
     * @return Coordenada Y de la poción.
     */
    public int getY() {
        return y;
    }

    /*
     * Método getNombre.
     * Devuelve el nombre de la poción.
     * @return Nombre de la poción.
     */
    public String getNombre() {
        return nombre;
    }

    /*
     * Método esUsada.
     * Indica si la poción ya ha sido usada o no.
     * @return true si la poción ha sido usada, false en caso contrario.
     */
    public boolean esUsada() {
        return usada;
    }
}