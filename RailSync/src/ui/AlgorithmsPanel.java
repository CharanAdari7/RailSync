package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import application.ApplicationController;

public class AlgorithmsPanel extends JPanel {

    private final ApplicationController controller;

    public AlgorithmsPanel(
            ApplicationController controller) {

        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(
                new Color(245, 246, 248));

        JLabel title =
                new JLabel("Algorithms");

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28));

        title.setBorder(
                BorderFactory.createEmptyBorder(
                        25, 28, 10, 28));

        add(title, BorderLayout.NORTH);

        JTextArea area =
                new JTextArea();

        area.setEditable(false);

        area.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        13));

        area.setText(

                "RAILSYNC ALGORITHM CATALOG\n"
                + "==========================\n\n"

                + "CO-1  STRING PROCESSING\n"
                + "-----------------------\n"
                + "KMP\n"
                + "  Pattern searching in railway text records.\n\n"
                + "Z-Function\n"
                + "  Prefix-based text matching.\n\n"
                + "Rabin-Karp\n"
                + "  Hash-based pattern searching.\n\n"
                + "Aho-Corasick\n"
                + "  Multiple-pattern searching.\n\n"

                + "CO-2  ADVANCED TEXT INDEXING\n"
                + "---------------------------\n"
                + "Suffix Array\n"
                + "  Fast indexing of railway text.\n\n"
                + "SA-IS\n"
                + "  Efficient suffix-array construction.\n\n"
                + "LCP / Kasai\n"
                + "  Longest common-prefix analysis.\n\n"
                + "Suffix Tree / Suffix Automata\n"
                + "  Advanced substring representation.\n\n"

                + "CO-3  OPTIMIZATION\n"
                + "-----------------\n"
                + "Levenshtein Distance\n"
                + "  Compare and correct textual records.\n\n"
                + "Damerau-Levenshtein\n"
                + "  Correction with transposition handling.\n\n"
                + "Bitmask Dynamic Programming\n"
                + "  Solve small combinational assignment problems.\n\n"
                + "Matrix Chain Multiplication\n"
                + "  Optimize chained computations.\n\n"
                + "Optimal BST\n"
                + "  Optimize ordered lookup structures.\n\n"

                + "CO-4  NETWORK OPTIMIZATION\n"
                + "-------------------------\n"
                + "Ford-Fulkerson\n"
                + "Edmonds-Karp\n"
                + "Dinic\n"
                + "Bipartite Matching\n"
                + "Konig's Theorem\n"
                + "Max-Flow Min-Cut\n"
                + "  Network capacity and assignment analysis.\n\n"

                + "CO-5  CONSTRAINTS\n"
                + "----------------\n"
                + "SAT / 3-SAT\n"
                + "  Constraint-based operational decisions.\n\n"
                + "3-SAT to CLIQUE\n"
                + "  Reduction of logical constraints.\n\n"
                + "CLIQUE to Independent Set\n"
                + "  Constraint transformation.\n\n"
                + "Independent Set to Vertex Cover\n"
                + "  Complementary constraint representation.\n\n"
                + "Vertex Cover 2-Approximation\n"
                + "  Approximate conflict resolution.\n\n"

                + "CO-6  RANDOMIZED & PARALLEL COMPUTING\n"
                + "-----------------------------------\n"
                + "Randomized QuickSort\n"
                + "  Efficient randomized ordering.\n\n"
                + "Reservoir Sampling\n"
                + "  Sampling from streaming records.\n\n"
                + "Miller-Rabin\n"
                + "  Probabilistic numeric validation.\n\n"
                + "Blelloch Scan\n"
                + "  Parallel prefix computation.\n\n"
                + "Parallel Reduce\n"
                + "  Parallel aggregation.\n\n"
                + "Brent's Theorem\n"
                + "  Parallel-work efficiency analysis.\n"
        );

        area.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 25, 20, 25));

        add(
                new JScrollPane(area),
                BorderLayout.CENTER);

        controller.setStatusMessage(
                "Algorithms selected");
    }
}