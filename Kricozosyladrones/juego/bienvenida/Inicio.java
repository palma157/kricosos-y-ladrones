package bienvenida;

import javax.swing.*;
import java.awt.Graphics;           //Paquete grafico basico de java para dibujar a la mazmorra y al jugador
import java.awt.event.KeyAdapter;   
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import laberinto.Mazmorra;
import personajes.Jugador;

// La clase Inicio es la que se encarga de inicializar el juego, manejar los eventos del teclado y dibujar los gráficos.
public class Inicio extends JPanel {

    // Atributos:
    private Mazmorra mazmorra;  // La mazmorra o laberinto del juego, que será dibujada.
    private Jugador jugador;    // El jugador que se moverá por el laberinto.

    // Constructor de la clase Inicio
    public Inicio() {
        // Inicializamos la mazmorra y el jugador.
        mazmorra = new Mazmorra();  
        jugador = new Jugador(mazmorra);  // El jugador recibe la referencia de la mazmorra para conocer su entorno.

        // Configuración del panel:
        setFocusable(true);  // Asegura que el panel pueda recibir eventos del teclado.
        
        // Agregamos un KeyListener para detectar cuándo se presionan las teclas.
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                jugador.teclaPresionada(e);  // El jugador reacciona a la tecla presionada.

                // Si el jugador llega al punto de destino, mostramos un mensaje y terminamos el juego.
                if (jugador.llegoAlPunto()) {
                    JOptionPane.showMessageDialog(null, "Felicidades, ¡COketo!");  // Mensaje cuando se gana.
                    System.exit(0);  // Cierra el juego.
                }
            }
        });
    }

    // Este método sobrescribe el método paintComponent de JPanel, que es donde se dibujan los componentes del juego.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // Llama al método de la superclase para asegurarse de que el panel se repinte correctamente.
        mazmorra.paint(g);  // Dibuja la mazmorra (laberinto).
        jugador.paint(g);   // Dibuja al jugador sobre la mazmorra.
    }

    // Método principal del juego.
    public static void main(String[] args) {
        // Muestra un cuadro de diálogo de bienvenida.
        JOptionPane.showMessageDialog(null, "Bienvenido a Kricosos y ladrones");

        // Creamos una ventana (JFrame) para el juego.
        JFrame miVentana = new JFrame("Maze Game");
        Inicio game = new Inicio();  // Creamos una instancia de la clase Inicio (el juego).

        // Añadimos el juego (panel) a la ventana.
        miVentana.add(game);
        miVentana.setSize(920, 540);  // Establecemos el tamaño de la ventana.
        miVentana.setLocation(300, 200);  // Establecemos la posición inicial de la ventana.
        miVentana.setVisible(true);  // Hacemos visible la ventana.
        miVentana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Configuramos la operación de cierre de la ventana.

        // Bucle principal del juego que se encarga de repintar la pantalla constantemente.
        while (true) {
            try {
                Thread.sleep(10);  // Hace una pausa breve para controlar la velocidad del repintado.
            } catch (InterruptedException ex) {
                // Captura excepciones si algo falla en el hilo.
                Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
            }
            game.repaint();  // Redibuja el juego continuamente para reflejar el movimiento del jugador y otros cambios.
        }
    }

    // Método que obtiene el nivel del juego. 
    // Actualmente solo devuelve 1, pero en un futuro puede manejar diferentes niveles.
    public static int obtieneNivel() {
        return 1;  // Retorna el nivel actual del juego.
    }
}
