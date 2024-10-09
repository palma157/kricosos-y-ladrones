package personajes;


//Importamos las clases necesarias para gráficos y eventos
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

//Importamos nuestras clases personalizadas
import laberinto.Mazmorra;
import laberinto.Pocion;

public class Jugador {
    // Atributos principales del jugador
    private Mazmorra mazmorra;                // Referencia a la mazmorra donde se encuentra el jugador
    private int x, y;                         // Posición actual del jugador en el mapa
    private int salud, ataque, defensa;       // Estadísticas del jugador
    private static final int movimiento = 40; // Tamaño del movimiento en píxeles por tecla presionada
    private int puntoX, puntoY;               // Coordenadas del punto de destino o meta en el mapa

    // Constructor de la clase Jugador
    public Jugador(Mazmorra mazmorra) {
        this.mazmorra = mazmorra;            // Asignamos la mazmorra
        this.x = 40;                         // Posición inicial en el eje X
        this.y = 40;                         // Posición inicial en el eje Y
        this.salud = 100;                    // Salud inicial del jugador
        this.ataque = 25;                    // Poder de ataque del jugador
        this.defensa = 5;                    // Nivel de defensa del jugador

     // Posición del punto final (meta) en la mazmorra
        this.puntoX = 400;
        this.puntoY = 400;

    }

    // Método para dibujar al jugador en el mapa
    public void paint(Graphics g) {
        g.setColor(Color.blue); // Establecemos el color azul para el jugador
        g.fillOval(x, y, 40, 40); // Dibujamos un círculo que representa al jugador
    }

 // Método que se ejecuta cuando se presiona una tecla
    public void teclaPresionada(KeyEvent evento) {
        int[][] maz = mazmorra.obtieneMazmorra(); // Obtenemos la matriz que representa la mazmorra
        int filaActual = y / 40;       // Calculamos la fila actual en la matriz
        int columnaActual = x / 40;    // Calculamos la columna actual en la matriz

        // Movimiento hacia la izquierda (tecla 'A')
        if (evento.getKeyCode() == KeyEvent.VK_A) {
            // Verificamos que no haya una pared y que no salgamos del límite izquierdo
            if (columnaActual - 1 >= 0 && maz[filaActual][columnaActual - 1] != 1) {
                x = x - movimiento; // Actualizamos la posición X del jugador
            }
        }
        // Movimiento hacia la derecha (tecla 'D')
        if (evento.getKeyCode() == KeyEvent.VK_D) {
            // Verificamos que no haya una pared y que no salgamos del límite derecho
            if (columnaActual + 1 < maz[0].length && maz[filaActual][columnaActual + 1] != 1) {
                x = x + movimiento; // Actualizamos la posición X del jugador
            }
        }
        // Movimiento hacia abajo (tecla 'S')
        if (evento.getKeyCode() == KeyEvent.VK_S) {
            // Verificamos que no haya una pared y que no salgamos del límite inferior
            if (filaActual + 1 < maz.length && maz[filaActual + 1][columnaActual] != 1) {
                y = y + movimiento; // Actualizamos la posición Y del jugador
            }
        }
        // Movimiento hacia arriba (tecla 'W')
        if (evento.getKeyCode() == KeyEvent.VK_W) {
            // Verificamos que no haya una pared y que no salgamos del límite superior
            if (filaActual - 1 >= 0 && maz[filaActual - 1][columnaActual] != 1) {
                y = y - movimiento; // Actualizamos la posición Y del jugador
            }
        }

        // Verificar si el jugador encuentra algún enemigo
        for (Enemigo enemigo : mazmorra.getEnemigos()) {
            // Si la posición del jugador coincide con la del enemigo y este no ha sido derrotado
            if (x == enemigo.getX() && y == enemigo.getY() && !enemigo.esDerrotado()) {
                iniciarCombate(enemigo);  // Iniciamos el combate con ese enemigo
            }
        }

        // Verificar si el jugador encuentra una poción
        for (Pocion pocion : mazmorra.getPociones()) {
            // Si la posición del jugador coincide con la de la poción y esta no ha sido usada
            if (x == pocion.getX() && y == pocion.getY() && !pocion.esUsada()) {
                pocion.usar(this);  // El jugador usa la poción
            }
        }

        // Comprobar si el jugador llega al punto objetivo
        if (llegoAlPunto()) {
            // Mostramos un mensaje de victoria y cerramos el juego
            JOptionPane.showMessageDialog(null, "Felicidades, ¡has ganado!,... No te han dicho que pareces un tazo dorado, ¡C0keto!");
            System.exit(0);
        }
    }

    // Método para iniciar el combate contra un enemigo
    private void iniciarCombate(Enemigo enemigo) {
        System.out.println("¡Combate contra " + enemigo.getNombre() + " comienza!");

        // Bucle que continúa hasta que el jugador o el enemigo sean derrotados
        while (this.salud > 0 && enemigo.getSalud() > 0) {
            // Turno del jugador
            System.out.println("Salud del jugador: " + salud + "/100, Ataque: " + ataque + ", Defensa: " + defensa);
            System.out.println(enemigo.getNombre() + " tiene " + enemigo.getSalud() + " de salud.");

            // El jugador ataca al enemigo
            System.out.println("Turno del jugador.");
            int danioJugador = ataque; // El daño que inflige el jugador
            enemigo.recibirDanio(danioJugador); // El enemigo recibe el daño
            System.out.println("Has atacado al enemigo con " + danioJugador + " puntos de daño.");

            // Verificamos si el enemigo ha sido derrotado
            if (enemigo.esDerrotado()) {
                System.out.println("¡Has derrotado al " + enemigo.getNombre() + "!");
                return; // Salimos del método ya que el enemigo ha sido derrotado
            }

            // Turno del enemigo
            System.out.println("Turno del enemigo.");
            int danioEnemigo = enemigo.atacar(); // El daño que inflige el enemigo
            recibirDanio(danioEnemigo); // El jugador recibe el daño
        }

        // Si la salud del jugador es 0 o menor, ha perdido la batalla
        if (this.salud <= 0) {
            System.out.println("Has perdido la batalla.");
        }
    }

    // Método para manejar el daño recibido por el jugador
    public void recibirDanio(int puntosDanio) {
        int danioFinal = puntosDanio - defensa; // Calculamos el daño real considerando la defensa
        if (danioFinal > 0) {
            salud -= danioFinal; // Restamos el daño a la salud del jugador
            System.out.println("Has recibido " + danioFinal + " puntos de daño. Salud restante: " + salud);
        } else {
            System.out.println("El ataque fue bloqueado."); // El ataque no fue suficiente para superar la defensa
        }
    }

    // Método para restablecer la salud del jugador al máximo
    public void restablecerSaludMaxima() {
        this.salud = 100; // Restauramos la salud al valor máximo
        System.out.println("Tu salud ha sido restaurada al máximo.");
    }

    // Método que verifica si el jugador ha llegado al punto objetivo
    public boolean llegoAlPunto() {
        return x == puntoX && y == puntoY; // Retorna true si las coordenadas coinciden
    }

    // Getters para obtener la posición actual del jugador en X
    public int getX() {
        return x;
    }

    // Getter para obtener la posición actual del jugador en Y
    public int getY() {
        return y;
    }
}