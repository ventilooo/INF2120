package tp3;

import tda.Individu;
import tda.Liste;
import util.Personne;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by SZAI29079604 on 04/04/2016.
 */
public class VueGenealogie {
    private JFrame frame;
    private Gen arbre;
    private DefaultListModel model;
    private JList genList;

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
        //
        JPanel panel = new JPanel(new FlowLayout());
        JPanel jListPanel = new JPanel(new FlowLayout());
        JPanel zoneTextePanel = new JPanel(new GridLayout(3, 2, 0, 5));
        //
        setJlist(jListPanel);
        setZoneTexte(zoneTextePanel);
        setCheckBox(panel);
        //
        panel.add(jListPanel);
        panel.add(zoneTextePanel);
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
        JMenuItem parentsMenuItem = new JMenuItem("Les parents");
        JMenuItem brothersMenuItem = new JMenuItem("Les frères");
        JMenuItem grandKidsMenuItem = new JMenuItem("Les petits enfants");

        explorationMenu.add(kidsMenuItem);
        explorationMenu.add(parentsMenuItem);
        explorationMenu.add(brothersMenuItem);
        explorationMenu.add(grandKidsMenuItem);
        menuBar.add(explorationMenu);

        //Menu Test
        JMenu menuTester = new JMenu("Tester");
        JMenuItem itemVerifier = new JMenuItem("Vérifier");
       /* itemVerifier.addActionListener(new ActionListener() {
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
        */ //TODO make that part of code usable !
        menuTester.add(itemVerifier);
        menuBar.add(menuTester);

        frame.setJMenuBar(menuBar);
    }

    private void setZoneTexte(JPanel panel) {
        JTextArea zoneTexte = new JTextArea("Test");
        zoneTexte.setBackground(Color.orange);
        frame.setBounds(100, 100, 600, 500);
        zoneTexte.setPreferredSize(new Dimension(300, 100));
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
        JLabel prenomLabel = new JLabel("PRÉNON:");
        JTextField prenomField = new JTextField(individu.lesPrenoms().toString());
        panel.add(prenomLabel);
        panel.add(prenomField);
    }

    private void setCheckBox(JPanel panel) {
        JCheckBox buttonNom = new JCheckBox("Nom");
        JCheckBox buttonPrenom = new JCheckBox("Prénom");
        JCheckBox buttonDate = new JCheckBox("Date");
        panel.add(buttonNom);
        panel.add(buttonPrenom);
        panel.add(buttonDate);
    }

    private void setJlist(JPanel panel) {
        genList = new JList(model);
        genList.setVisible(false);
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
                    model.addElement(moi);
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

    private void leParentDialog(JDialog jDialog) {
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
        JLabel instruction = new JLabel("Est-ce le premier parent ?");
        JButton ouiButton = new JButton("OUI");
        JButton annulerButton = new JButton("Annuler");
        JButton nonButton = new JButton("NON");
        jDialog.add(instruction);
        jDialog.add(ouiButton);
        jDialog.add(nonButton);
        jDialog.add(annulerButton);
        ouiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.removeAll();
                leParentDialog(jDialog);
            }
        });
        nonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jDialog.removeAll();
                leParentDialog(jDialog);
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

    private Personne getJlistSelectedIndividu() {
        return (Personne) genList.getSelectedValue();
    }

    /*private  void displayContentInZoneDeTexte(JPanel panel){
        buttonNom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonNom.isSelected()){
                    setNomInZoneDeText(getJlistSelectedIndividu(),panel);
                }
            }
        });
        buttonPrenom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonPrenom.isSelected()){
                    setPrenomInZoneDeText(getJlistSelectedIndividu(),panel);
                }
            }
        });
        buttonDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (buttonDate.isSelected()){
                    setDateInZoneDeText(getJlistSelectedIndividu(),panel);
                }
            }
        });
    }*/

}
