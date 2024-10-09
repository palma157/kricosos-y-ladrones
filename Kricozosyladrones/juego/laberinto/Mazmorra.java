package laberinto;

import java.awt.Graphics;
import java.awt.Color;  // Importa la clase Color para manejar los colores al dibujar en el mapa
import java.awt.Container;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;  // Importa List para manejar listas
import personajes.Enemigo;  // Importa la clase Enemigo
import personajes.Jugador;  // Importa la clase Jugador
import bienvenida.Inicio;   // Importa la clase Inicio

// Clase Mazmorra representa la estructura de la mazmorra en el juego
public class Mazmorra {
    // Atributos
    private List<Pocion> pociones;  // Lista de pociones colocadas en la mazmorra
    private List<Enemigo> enemigos;  // Lista de enemigos que aparecerán en la mazmorra
    private int[][] maz;  // Matriz que representa el mapa de la mazmorra
    private int numeroFilas;  // Número de filas en el mapa
    private int numeroColumnas;  // Número de columnas en el mapa
    private int anchoBloque;  // Ancho de cada bloque que compone la mazmorra
    private int altoBloque;  // Alto de cada bloque que compone la mazmorra

    // Constructor de la clase Mazmorra
    public Mazmorra() {
        // Inicialización de las dimensiones de la mazmorra
        this.numeroFilas = 13;  // Número de filas del mapa
        this.numeroColumnas = 13;  // Número de columnas del mapa
        this.anchoBloque = 40;  // Ancho de cada bloque
        this.altoBloque = 40;  // Alto de cada bloque

        // Inicializa el mapa de la mazmorra con una matriz vacía
        maz = new int[numeroFilas][numeroColumnas];

        // Inicializa la lista de enemigos en la mazmorra
        enemigos = new ArrayList<>();  // ArrayList se usa aquí porque permite agregar y eliminar enemigos de manera dinámica

        // Añadir enemigos en diferentes posiciones con atributos predefinidos
        enemigos.add(new Enemigo("Chota", 20, 10, 5, 120, 120));
        enemigos.add(new Enemigo("Ladron", 55, 15, 10, 200, 240));
        enemigos.add(new Enemigo("Kricoso", 70, 15, 15, 360, 400));

        // Inicializa la lista de pociones en la mazmorra
        pociones = new ArrayList<>();  // ArrayList se usa aquí por su flexibilidad al manejar listas dinámicas de pociones

        // Añadir pociones en diferentes posiciones
        pociones.add(new Pocion(120, 160, "Bacha"));
    }

    /*
     * Método paint.
     * Dibuja la mazmorra, sus bloques, los enemigos y las pociones.
     * @param g Objeto Graphics para dibujar en la pantalla.
     */
    public void paint(Graphics g) {
        // Obtiene la matriz de la mazmorra
        int[][] mazmorra = obtieneMazmorra();

        // Recorre la matriz para dibujar los bloques en las posiciones correctas
        for (int fila = 0; fila < numeroFilas; fila++) {
            for (int columna = 0; columna < numeroColumnas; columna++) {
                if (mazmorra[fila][columna] == 1) {  // Si es un bloque sólido
                    if (Inicio.obtieneNivel() == 1) {  // Cambia el color dependiendo del nivel
                        g.setColor(Color.darkGray);  // Color del bloque
                    }
                    // Dibuja el bloque
                    g.fillRect(columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque);
                    g.setColor(Color.black);  // Color para el contorno del bloque
                    g.drawRect(columna * anchoBloque, fila * altoBloque, anchoBloque, altoBloque);
                }
            }
        }

        // Dibuja textos en el mapa en posiciones específicas
        g.drawString("Changarro", 1 * anchoBloque + 10, 1 * altoBloque + 30);  // Posición del punto de inicio
        g.drawString("Punto", 10 * anchoBloque + 10, 10 * altoBloque + 30);  // Posición del punto final

        // Dibuja los enemigos en el mapa si no han sido derrotados
        for (Enemigo enemigo : enemigos) {
            if (!enemigo.esDerrotado()) {  // Dibuja solo a los enemigos que no han sido derrotados
                enemigo.paint(g);
            }
        }

        // Dibuja las pociones en el mapa si no han sido usadas
        for (Pocion pocion : pociones) {
            pocion.paint(g);  // Se dibuja cada poción si no ha sido usada
        }
    }

    /*
     * Método getPociones.
     * Devuelve la lista de pociones que hay en la mazmorra.
     * @return Lista de objetos Pocion.
     */
    public List<Pocion> getPociones() {
        return pociones;
    }

    /*
     * Método getEnemigos.
     * Devuelve la lista de enemigos que hay en la mazmorra.
     * @return Lista de objetos Enemigo.
     */
    public List<Enemigo> getEnemigos() {
        return enemigos;
    }

    /*
     * Método iniciarCombate.
     * Inicia el combate entre el jugador y un enemigo hasta que uno de los dos es derrotado.
     * @param enemigo El enemigo con el que el jugador combate.
     * @param jugador El jugador que participa en el combate.
     */
    public void iniciarCombate(Enemigo enemigo, Jugador jugador) {
        // Muestra el inicio del combate con detalles de ambos personajes
        System.out.println("¡Combate contra " + enemigo.getNombre() + " comienza!");
        System.out.println("Salud del jugador: " + jugador.getSalud() + "/" + jugador.getSaludMaximo() + ", Ataque: " + jugador.getAtaque() + ", Defensa: " + jugador.getDefensa());
        System.out.println(enemigo.getNombre() + " tiene " + enemigo.getSalud() + " de salud.");

        // El combate continúa hasta que uno de los dos personajes es derrotado
        while (jugador.getSalud() > 0 && enemigo.getSalud() > 0) {
            // Turno del jugador
            System.out.println("Turno del jugador.");
            int danioAlEnemigo = jugador.atacar();  // El jugador ataca
            enemigo.recibirDanio(danioAlEnemigo);   // El enemigo recibe daño

            // Verifica si el enemigo ha sido derrotado
            if (enemigo.getSalud() <= 0) {
                System.out.println("¡Has derrotado al " + enemigo.getNombre() + "!");
                removerEnemigo(enemigo);  // Elimina al enemigo de la lista
                break;  // Termina el combate
            }

            // Turno del enemigo
            System.out.println("Turno del enemigo.");
            int danioAlJugador = enemigo.atacar();  // El enemigo ataca
            jugador.recibirDanio(danioAlJugador);   // El jugador recibe daño

            // Muestra la salud actual del jugador
            System.out.println("Salud del jugador: " + jugador.getSalud() + "/" + jugador.getSaludMaximo());
            System.out.println(enemigo.getNombre() + " tiene " + enemigo.getSalud() + " de salud.");
        }

        // Verifica si el jugador ha sido derrotado
        if (jugador.getSalud() <= 0) {
            System.out.println("¡Has sido derrotado!");
        }

        // Repinta la mazmorra para actualizar la pantalla después del combate
        Container container = new Container();
        container.repaint();
    }

    /*
     * Método removerEnemigo.
     * Elimina al enemigo de la lista después de ser derrotado.
     * @param enemigo El enemigo que ha sido derrotado.
     */
    private void removerEnemigo(Enemigo enemigo) {
        enemigos.remove(enemigo);  // Elimina el enemigo de la lista
        System.out.println(enemigo.getNombre() + " ha sido removido de la mazmorra.");
    }

    /*
     * Método obtieneMazmorra.
     * Crea y devuelve la matriz que representa el mapa de la mazmorra.
     * @return Matriz de enteros que representa el mapa.
     */
    public int[][] obtieneMazmorra() {
        if (Inicio.obtieneNivel() == 1) {
            // Definición del mapa con 1s como paredes y 0s como caminos
            int[][] mazmorra = {
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1},
                {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1},
                {1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1},
                {1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            };
            return mazmorra;
        }
        return maz;
    }
}

