package personajes;

import java.awt.Graphics;
import java.awt.Color;

// Clase Enemigo representa a un enemigo en el juego.
public class Enemigo {

    // Atributos de la clase Enemigo.
    private String nombre;  // Nombre del enemigo
    private int salud;      // Puntos de salud del enemigo
    private int ataque;     // Puntos de ataque del enemigo
    private int defensa;    // Puntos de defensa del enemigo
    private int x, y;       // Coordenadas (x, y) para la posición del enemigo en el mapa
    private boolean derrotado;  // Estado del enemigo: true si está derrotado, false si aún está vivo

    /*
     * Constructor de la clase Enemigo.
     * Inicializa el nombre, salud, ataque, defensa, posición (x, y) y estado de derrotado.
     * @param nombre Nombre del enemigo
     * @param salud Puntos de salud iniciales
     * @param ataque Puntos de ataque
     * @param defensa Puntos de defensa
     * @param x Coordenada X del enemigo en el mapa
     * @param y Coordenada Y del enemigo en el mapa
     */
    public Enemigo(String nombre, int salud, int ataque, int defensa, int x, int y) {
        this.nombre = nombre;
        this.salud = salud;
        this.ataque = ataque;
        this.defensa = defensa;
        this.x = x;
        this.y = y;
        this.derrotado = false;  // El enemigo empieza sin estar derrotado
    }

    /*
     * Método paint.
     * Se encarga de dibujar al enemigo en la pantalla usando gráficos.
     * Si el enemigo no ha sido derrotado, lo dibuja como un óvalo negro.
     * @param g Objeto Graphics para dibujar en la pantalla.
     */
    public void paint(Graphics g) {
        if (!derrotado) {
            g.setColor(Color.black);  // Color del enemigo
            g.fillOval(x, y, 40, 40); // Dibujar al enemigo como un óvalo en su posición
        }
    }

    /*
     * Método recibirDanio.
     * Permite que el enemigo reciba daño, reduciendo su salud después de aplicar su defensa.
     * Si la salud del enemigo baja a 0 o menos, se marca como derrotado.
     * @param danio Cantidad de daño que recibe el enemigo.
     */
    public void recibirDanio(int danio) {
        int danioFinal = danio - defensa;  // El daño se reduce según la defensa del enemigo
        if (danioFinal > 0) {  // Solo aplica daño si supera la defensa
            salud -= danioFinal;  // Reduce la salud
        } else {
            System.out.println("El ataque fue bloqueado por la defensa del enemigo.");
        }
        if (salud <= 0) {
            derrotado = true;  // El enemigo es derrotado si su salud llega a 0
        }
    }

    /*
     * Método esDerrotado.
     * Devuelve el estado del enemigo, si está derrotado o no.
     * @return true si el enemigo está derrotado, false si sigue vivo.
     */
    public boolean esDerrotado() {
        return derrotado;
    }

    /*
     * Método getNombre.
     * Devuelve el nombre del enemigo.
     * @return Nombre del enemigo.
     */
    public String getNombre() {
        return nombre;
    }

    /*
     * Método getSalud.
     * Devuelve la cantidad actual de salud del enemigo.
     * @return Salud actual del enemigo.
     */
    public int getSalud() {
        return salud;
    }

    /*
     * Método getAtaque.
     * Devuelve el valor de ataque del enemigo.
     * @return Puntos de ataque del enemigo.
     */
    public int getAtaque() {
        return ataque;
    }

    /*
     * Método atacar.
     * El enemigo realiza un ataque y devuelve el valor de su ataque.
     * @return El valor del ataque realizado por el enemigo.
     */
    public int atacar() {
        return ataque;  // Retorna el ataque del enemigo
    }

    /*
     * Método getX.
     * Devuelve la coordenada X del enemigo en el mapa.
     * @return La posición X del enemigo.
     */
    public int getX() {
        return x;
    }

    /*
     * Método getY.
     * Devuelve la coordenada Y del enemigo en el mapa.
     * @return La posición Y del enemigo.
     */
    public int getY() {
        return y;
    }
}
