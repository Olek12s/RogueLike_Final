package world.generation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* This code is part of an article, http://gpfault.net/posts/perlin-noise.txt.html */
public class Perlin {

    // Parametry konfiguracyjne
    private static final int WIDTH = 800; // Szerokość obrazu
    private static final int HEIGHT = 800; // Wysokość obrazu
    private static final float SCALE = 128.0f; // Skala szumu
    private static final float THRESHOLD = 0.5f; // Próg do rozróżnienia ścian i jaskiń

    // Prosta klasa wektora 2D
    static class Vec2 {
        public float x, y;

        public Vec2(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public Vec2 subtract(Vec2 v) {
            return new Vec2(this.x - v.x, this.y - v.y);
        }

        public float dot(Vec2 v) {
            return this.x * v.x + this.y * v.y;
        }

        public Vec2 normalize() {
            float length = (float) Math.sqrt(x * x + y * y);
            if (length == 0) return new Vec2(0, 0);
            return new Vec2(x / length, y / length);
        }
    }

    // Tabela permutacji dla wyboru gradientów
    private int[] permutation = new int[512];

    /**
     * Konstruktor inicjalizujący tabelę permutacji na podstawie podanego nasionka.
     *
     * @param seed Nasionko do losowania permutacji.
     */
    public Perlin(long seed) {
        initializePermutation(seed);
    }

    /**
     * Inicjalizuje tabelę permutacji przy użyciu podanego nasionka.
     *
     * @param seed Nasionko do losowania permutacji.
     */
    private void initializePermutation(long seed) {
        // Inicjalizacja listy permutacji z wartościami od 0 do 255
        List<Integer> pList = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            pList.add(i);
        }

        // Tasowanie listy przy użyciu podanego nasionka
        Collections.shuffle(pList, new Random(seed));

        // Wypełnianie tablicy permutacji poprzez duplikację listy
        for (int i = 0; i < 512; i++) {
            permutation[i] = pList.get(i % 256);
        }
    }

    // Generowanie wektora gradientu na podstawie tabeli permutacji
    Vec2 grad(int hash) {
        switch (hash & 3) {
            case 0:
                return new Vec2(1, 1);
            case 1:
                return new Vec2(-1, 1);
            case 2:
                return new Vec2(1, -1);
            case 3:
                return new Vec2(-1, -1);
            default:
                return new Vec2(1, 0); // Nie powinno się zdarzyć
        }
    }

    /* Krzywa S dla 0 <= t <= 1 */
    float fade(float t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    /* Szum 2D */
    float noise(Vec2 p) {
        int xi = (int) Math.floor(p.x) & 255;
        int yi = (int) Math.floor(p.y) & 255;

        float xf = p.x - (int) Math.floor(p.x);
        float yf = p.y - (int) Math.floor(p.y);

        float u = fade(xf);
        float v = fade(yf);

        int aa = permutation[permutation[xi] + yi];
        int ab = permutation[permutation[xi] + yi + 1];
        int ba = permutation[permutation[xi + 1] + yi];
        int bb = permutation[permutation[xi + 1] + yi + 1];

        Vec2 gradAA = grad(aa).normalize();
        Vec2 gradBA = grad(ba).normalize();
        Vec2 gradAB = grad(ab).normalize();
        Vec2 gradBB = grad(bb).normalize();

        Vec2 deltaAA = new Vec2(xf, yf);
        Vec2 deltaBA = new Vec2(xf - 1, yf);
        Vec2 deltaAB = new Vec2(xf, yf - 1);
        Vec2 deltaBB = new Vec2(xf - 1, yf - 1);

        float dotAA = gradAA.dot(deltaAA);
        float dotBA = gradBA.dot(deltaBA);
        float dotAB = gradAB.dot(deltaAB);
        float dotBB = gradBB.dot(deltaBB);

        float lerpX1 = lerp(dotAA, dotBA, u);
        float lerpX2 = lerp(dotAB, dotBB, u);

        return lerp(lerpX1, lerpX2, v);
    }

    // Interpolacja liniowa
    float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    /**
     * Generuje obraz szumu Perlin na podstawie podanego nasionka.
     *
     * @param seed   Nasionko do generowania szumu.
     * @param width  Szerokość obrazu.
     * @param height Wysokość obrazu.
     * @return Obraz BufferedImage przedstawiający system jaskiń.
     */
    private BufferedImage generateNoiseImage(long seed, int width, int height) {
        // Inicjalizacja generatora szumu Perlin z podanym nasionkiem
        Perlin perlin = new Perlin(seed);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Generowanie szumu Perlin
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                float nx = x / SCALE;
                float ny = y / SCALE;
                Vec2 point = new Vec2(nx, ny);

                // Kombinacja wielu oktaw szumu
                float n = perlin.noise(point) * 1.0f +
                        perlin.noise(new Vec2(nx * 4, ny * 4)) * 0.9f + // Wyższa waga dla wyższych oktaw
                        perlin.noise(new Vec2(nx * 9, ny * 9)) * 0.5f +
                        perlin.noise(new Vec2(nx * 7, ny * 7)) * 0.25f;

                // Normalizacja do [0,1]
                n = (n + 1) / 2.0f;

                // Ograniczenie wartości
                n = Math.min(Math.max(n, 0f), 1f);

                // Zastosowanie progu do rozróżnienia ścian i jaskiń
                // Jeśli n > THRESHOLD, to jaskinia (jasny obszar), w przeciwnym razie ściana (ciemny obszar)
                int color = (n > THRESHOLD) ? Color.WHITE.getRGB() : Color.BLACK.getRGB();

                image.setRGB(x, y, color);
            }
        }

        return image;
    }

    public static void main(String[] args) {
        // Tworzenie głównego JFrame
        JFrame frame = new JFrame("Perlin Noise Cave System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Inicjalizacja nasionka
        final Random random = new Random();
        final long[] currentSeed = {random.nextLong()};
        System.out.println("Initial seed: " + currentSeed[0]);

        // Inicjalizacja i generowanie początkowego obrazu
        Perlin perlin = new Perlin(currentSeed[0]);
        BufferedImage initialImage = perlin.generateNoiseImage(currentSeed[0], WIDTH, HEIGHT);

        // Tworzenie JLabel do wyświetlania obrazu
        JLabel imageLabel = new JLabel(new ImageIcon(initialImage));
        frame.add(imageLabel, BorderLayout.CENTER);

        // Tworzenie panelu kontrolnego
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Wyświetlanie aktualnego nasionka
        JLabel seedLabel = new JLabel("Seed: " + currentSeed[0]);
        controlPanel.add(seedLabel);

        // Tworzenie przycisku "Regenerate"
        JButton regenerateButton = new JButton("Regenerate");
        controlPanel.add(regenerateButton);

        // Dodanie panelu kontrolnego do ramki
        frame.add(controlPanel, BorderLayout.SOUTH);

        // Obsługa zdarzenia kliknięcia przycisku "Regenerate"
        regenerateButton.addActionListener(e -> {
            // Generowanie nowego losowego nasionka
            long newSeed = random.nextLong();
            currentSeed[0] = newSeed;
            System.out.println("Regenerating with seed: " + newSeed);

            // Aktualizacja etykiety nasionka
            seedLabel.setText("Seed: " + newSeed);

            // Generowanie nowego obrazu szumu
            BufferedImage newImage = perlin.generateNoiseImage(newSeed, WIDTH, HEIGHT);

            // Aktualizacja obrazu w JLabel
            imageLabel.setIcon(new ImageIcon(newImage));
        });

        // Finalizacja i wyświetlenie ramki
        frame.pack();
        frame.setSize(WIDTH, HEIGHT + 50); // Dodatkowa przestrzeń dla panelu kontrolnego
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
