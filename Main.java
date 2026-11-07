import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;

public class Main {

    public static void main(String[] args) {
        Space space = new Space(30, 30);

        for (int i = 0; i < 20; i++) {
            space.addEntity(new Asteroid(space.randomPosition()));
        }

        for (int i = 0; i < 6; i++) {
            space.addEntity(new MiningShip(space.randomPosition()));
        }

        for (int i = 0; i < 3; i++) {
            space.addEntity(new PirateShip(space.randomPosition()));
        }

        JFrame frame = new JFrame("Space Mining Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SimulationPanel panel = new SimulationPanel(space);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        SimulationStats stats = new SimulationStats(space);
        SpaceEvents events = new SpaceEvents();
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        JLabel statsLabel = new JLabel(stats.getSummary());
        JLabel eventLabel = new JLabel("Latest event: " + events.getLastEvent());
        infoPanel.add(statsLabel);
        infoPanel.add(eventLabel);
        frame.add(infoPanel, BorderLayout.SOUTH);

        frame.setSize(620, 640);
        frame.setVisible(true);

        Timer timer = new Timer(300, event -> {
            space.tick();
            events.update(space);
            statsLabel.setText(stats.getSummary());
            eventLabel.setText("Latest event: " + events.getLastEvent());
            panel.repaint();
        });

        timer.start();
    }
}

class SimulationPanel extends JPanel {
    private Space space;

    public SimulationPanel(Space space) {
        this.space = space;
        setBackground(Color.BLACK);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cellWidth = getWidth() / space.getWidth();
        int cellHeight = getHeight() / space.getHeight();

        for (SpaceEntity entity : space.getEntities()) {
            int x = entity.getPosition().getX() * cellWidth;
            int y = entity.getPosition().getY() * cellHeight;

            if (entity instanceof Asteroid) {
                g.setColor(Color.LIGHT_GRAY);
            } else if (entity instanceof MiningShip) {
                g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }

            g.drawString(
                    String.valueOf(entity.getSymbol()),
                    x + cellWidth / 2,
                    y + cellHeight / 2
            );
        }
    }
}
