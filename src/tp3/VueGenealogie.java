package tp3;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import tda.Individu;
import tda.Liste;
import test.TestGenealogie;
import util.Personne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * Created by SZAI29079604 on 04/04/2016.
 */
public class VueGenealogie {
    protected Gen arbre;
    private JFrame frame;
    private DefaultListModel model;
    private JList genList;
    private Personne active;
    private JTextArea zoneTexte;

    public VueGenealogie() {
        initialize();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    VueGenealogie window = new VueGenealogie();
                    window.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initialize() {
        frame = new JFrame();
        arbre = new Gen();
        model = new DefaultListModel();
        test();
        //
        JPanel panel = new JPanel(new FlowLayout());
        JPanel jListPanel = new JPanel(new FlowLayout());
        JPanel zoneDeTexte = new JPanel(new GridLayout(3, 2, 0, 5));
        //
        setJlist(jListPanel);
        setZoneTexte(zoneDeTexte);
        setCheckBox(panel);
        //
        panel.add(jListPanel);
        panel.add(zoneDeTexte);
        createToolBar(frame);
        //
        frame.add(panel);
    }

    private void createToolBar(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        //Menu Gérer
        JMenu gestionMenu = new JMenu("Gérer");
        JMenuItem addIndividu = new JMenuItem("Ajouter un individu");
        addIndividu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addIndividuDialog();
            }
        });
        JMenuItem addParents = new JMenuItem("Ajouetr un parent");
        addParents.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addParentDialog();
            }
        });
        JMenuItem displayIndividu = new JMenuItem("Afficher les individus");
        displayIndividu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                for (Object personne : arbre.lesIndividus()) {
                    model.addElement(personne);
                }
                genList.setVisible(true);
            }
        });
        JMenuItem exit = new JMenuItem("Quitter");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gestionMenu.add(addIndividu);
        gestionMenu.add(addParents);
        gestionMenu.add(displayIndividu);
        gestionMenu.add(exit);
        menuBar.add(gestionMenu);

        //Menu Explorer
        JMenu explorationMenu = new JMenu("Exploration");
        JMenuItem kidsMenuItem = new JMenuItem("Les enfants");
        kidsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                for (Individu personne : getArrayOfIndividu(arbre.lesEnfants(null, active))) {
                    model.addElement(personne);
                    genList.setVisible(true);
                }
            }
        });
        JMenuItem parentsMenuItem = new JMenuItem("Les parents");
        parentsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                for (Individu personne : getArrayOfIndividu(arbre.lesParents(active))) {
                    model.addElement(personne);
                    genList.setVisible(true);
                }
            }
        });
        JMenuItem brothersMenuItem = new JMenuItem("Les frères");
        brothersMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                for (Individu personne : getArrayOfIndividu(arbre.laFratrie(active))) {
                    model.addElement(personne);
                    genList.setVisible(true);
                }
            }
        });
        JMenuItem grandKidsMenuItem = new JMenuItem("Les petits enfants");
        grandKidsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                for (Individu personne : getArrayOfIndividu(arbre.lesPetitsEnfants(null, active))) {
                    model.addElement(personne);
                    genList.setVisible(true);
                }
            }
        });

        explorationMenu.add(kidsMenuItem);
        explorationMenu.add(parentsMenuItem);
        explorationMenu.add(brothersMenuItem);
        explorationMenu.add(grandKidsMenuItem);
        menuBar.add(explorationMenu);

        //Menu Test
        JMenu menuTester = new JMenu("Tester");
        JMenuItem itemVerifier = new JMenuItem("Vérifier");
        itemVerifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                zoneTexte.setText("");
                if (arbre != null) {
                    TestGenealogie.definirGenealogie(arbre);
                    Result res = JUnitCore.runClasses(TestGenealogie.class);
                    if (res.getFailureCount() == 0) {
                        zoneTexte.append("Pas d'erreurs");
                    } else {
                        for (Failure fail : res.getFailures()) {
                            zoneTexte.append(fail.getTestHeader() + "\n");
                            zoneTexte.append(fail.getMessage() + "\n");
                        }
                    }
                } else {
                    zoneTexte.append("Le graphe n'est pas défini");
                }
            }
        });
        menuTester.add(itemVerifier);
        menuBar.add(menuTester);

        frame.setJMenuBar(menuBar);
    }

    private void setZoneTexte(JPanel panel) {
        zoneTexte = new JTextArea();
        zoneTexte.setBackground(Color.orange);
        frame.setBounds(300, 300, 1000, 1000);
        zoneTexte.setPreferredSize(new Dimension(500, 350));
        panel.add(zoneTexte);
    }

    private void setNomInZoneDeText(Personne individu, JPanel panel) {
        JLabel nomLabel = new JLabel("NOM:");
        JTextField nomField = new JTextField(individu.leNom());
        panel.add(nomLabel);
        panel.add(nomField);
    }

    private void setDateInZoneDeText(Personne individu, JPanel panel) {
        JLabel dateLabel = new JLabel("NOM:");
        JTextField dateField = new JTextField(individu.laDate().toString());
        panel.add(dateLabel);
        panel.add(dateField);
    }

    private void setPrenomInZoneDeText(Personne individu, JPanel panel) {
        JLabel prenomLabel = new JLabel("PRÉNOM:");
        JTextField prenomField = new JTextField(individu.lesPrenoms().toString());
        panel.add(prenomLabel);
        panel.add(prenomField);
    }

    private void setCheckBox(JPanel panel) {
        JCheckBox buttonNom = new JCheckBox("Nom");
        JCheckBox buttonPrenom = new JCheckBox("Prénom");
        JCheckBox buttonDate = new JCheckBox("Date");
        active = (Personne) genList.getSelectedValue();
        buttonNom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonNom.isSelected()) {
                    active = (Personne) genList.getSelectedValue();
                    setNomInZoneDeText(active, panel);
                }
            }
        });
        buttonPrenom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonPrenom.isSelected()) {
                    active = (Personne) genList.getSelectedValue();
                    setPrenomInZoneDeText(active, panel);
                }
            }
        });
        buttonDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonDate.isSelected()) {
                    active = (Personne) genList.getSelectedValue();
                    setDateInZoneDeText(active, panel);
                }
            }
        });
        panel.add(buttonNom);
        panel.add(buttonPrenom);
        panel.add(buttonDate);
    }

    private void setJlist(JPanel panel) {
        genList = new JList(model);
        panel.add(genList);
    }

    private void addIndividuDialog() {
        JDialog jDialog = new JDialog();
        jDialog.setTitle("Ajouter Individu");
        jDialog.setSize(300, 200);
        jDialog.setVisible(true);
        jDialog.setLayout(new FlowLayout(FlowLayout.TRAILING));
        addIndividuDialogComponents(jDialog);

    }

    private void addIndividuDialogComponents(JDialog jDialog) {
        JLabel nameLabel = new JLabel("Nom:");
        final JTextField nameField = new JTextField(21);
        JLabel prenomLabel = new JLabel("Prénoms:", SwingConstants.CENTER);
        final JTextField prenomsField = new JTextField(19);
        JLabel dateLabel = new JLabel("Date de naissance sous format jj-mm-yyy");
        final JTextField dateField = new JTextField(20);
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DateFormat dfm = new SimpleDateFormat("dd-MM-YYYY");
                    Personne moi = new Personne(nameField.getText(), getPrenoms(prenomsField.getText()), dfm.parse(dateField.getText()));
                    arbre.ajout(moi);
                    jDialog.dispose();
                } catch (ParseException E) {
                    E.printStackTrace();
                }
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
        jDialog.add(nameLabel);
        jDialog.add(nameField);
        jDialog.add(prenomLabel);
        jDialog.add(prenomsField);
        jDialog.add(dateLabel);
        jDialog.add(dateField);
        jDialog.add(okButton);
        jDialog.add(cancelButton);
    }

    private ArrayList<String> getPrenoms(String str) {
        StringTokenizer tokenizer = new StringTokenizer(str);
        ArrayList<String> stringList = new ArrayList<>();
        while (tokenizer.hasMoreElements()) {
            stringList.add(tokenizer.nextElement().toString());
        }
        return stringList;
    }

    private void addParentDialog() {
        JDialog jDialog = new JDialog();
        jDialog.setTitle("Choix du parent");
        jDialog.setSize(300, 200);
        jDialog.setVisible(true);
        jDialog.setLayout(new FlowLayout(FlowLayout.CENTER));
        choixDuParent(jDialog);
    }

    private void leParentDialog() {
        JDialog jDialog = new JDialog();
        jDialog.setSize(300, 200);
        jDialog.setVisible(true);
        jDialog.setLayout(new FlowLayout(FlowLayout.CENTER));
        jDialog.setTitle("Le parent");
        JLabel instruction = new JLabel("Choix d'un parent");
        JComboBox parentsBox = new JComboBox(getArrayPersone());
        JButton ouiButton = new JButton("OUI");
        JButton annulerButton = new JButton("Annuler");
        jDialog.add(instruction);
        jDialog.add(parentsBox);
        jDialog.add(ouiButton);
        jDialog.add(annulerButton);
        ouiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Individu activeIndividu = (Individu) parentsBox.getSelectedItem();
                active = (Personne) genList.getSelectedValue();
                arbre.definirParent1(active, activeIndividu);
                jDialog.dispose();
            }
        });
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });

    }

    private void choixDuParent(JDialog jDialog) {
        JLabel instruction2 = new JLabel("Est-ce le premier parent ?");
        JButton ouiButton = new JButton("OUI");
        JButton annulerButton = new JButton("Annuler");
        JButton nonButton = new JButton("NON");
        jDialog.add(instruction2);
        jDialog.add(ouiButton);
        jDialog.add(nonButton);
        jDialog.add(annulerButton);
        ouiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
                leParentDialog();
            }
        });
        nonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
                leParentDialog();
            }
        });
        annulerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.dispose();
            }
        });
    }

    private Individu[] getArrayPersone() {
        Individu[] arr = new Personne[arbre.lesIndividus().longueur()];
        for (int i = 0; i < arbre.lesIndividus().longueur(); i++) {
            arr[i] = arbre.lIndividu(i);
        }
        return arr;
    }

    private Individu[] getArrayOfIndividu(Liste liste) {
        Individu[] arr = new Personne[liste.longueur()];
        for (int i = 0; i < liste.longueur(); i++) {
            arr[i] = arbre.lIndividu(i);
        }
        return arr;
    }

    private void test() {
        DateFormat dfm = new SimpleDateFormat("Y");
        dfm.setTimeZone(TimeZone.getTimeZone("Canada/Montreal"));
        Personne[] testTab = new Personne[15];
        try {
            testTab[0] = new Personne("Tremblay", new ArrayList<String>(Arrays.asList("Guy")), dfm.parse("1990"));
            testTab[1] = new Personne("Mili", new ArrayList<String>(Arrays.asList("Hafedh")), dfm.parse("1994"));
            testTab[2] = new Personne("Gagnon", new ArrayList<String>(Arrays.asList("Étienne")), dfm.parse("1991"));
            testTab[3] = new Personne("Laforest", new ArrayList<String>(Arrays.asList("Louise")), dfm.parse("1992"));
            testTab[4] = new Personne("Cherkaoui", new ArrayList<String>(Arrays.asList("Omar")), dfm.parse("1967"));
            testTab[5] = new Personne("Kerhervé", new ArrayList<String>(Arrays.asList("Brigitte")), dfm.parse("1965"));
            testTab[6] = new Personne("Moha", new ArrayList<String>(Arrays.asList("Naouel")), dfm.parse("1964"));
            testTab[7] = new Personne("Boukadoum", new ArrayList<String>(Arrays.asList("Mounir")), dfm.parse("1963"));
            testTab[8] = new Personne("Villemaire", new ArrayList<String>(Arrays.asList("Roger")), dfm.parse("1945"));
            testTab[9] = new Personne("Dupuis", new ArrayList<String>(Arrays.asList("Robert")), dfm.parse("1940"));
            testTab[10] = new Personne("Gabrini", new ArrayList<String>(Arrays.asList("Philippe")), dfm.parse("1941"));
            testTab[11] = new Personne("Bergeron", new ArrayList<String>(Arrays.asList("Anne")), dfm.parse("1923"));
            testTab[12] = new Personne("Davidson", new ArrayList<String>(Arrays.asList("Paul")), dfm.parse("1948"));
            testTab[13] = new Personne("Makarenkov", new ArrayList<String>(Arrays.asList("Vladimir")), dfm.parse("1962"));
            testTab[14] = new Personne("Bouisset", new ArrayList<String>(Arrays.asList("Marc")), dfm.parse("1922"));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < testTab.length; i++) {
            arbre.ajout(testTab[i]);
        }
    }


}
