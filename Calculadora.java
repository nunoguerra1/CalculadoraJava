import java.awt.*; 
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Calculadora { 
    int boardWidth = 360; // tamanho da janela
    int boardHeight = 540; // tamanho da janela
   
    Color cinzaClaro = new Color(212, 212, 210); //criação cor 
    Color cinzaEscuro = new Color(80, 80, 80); //criação cor
    Color preto = new Color(28, 28, 28); //criação cor
    Color laranja = new Color(255, 149, 0); //criação cor

    String[] botõesValores = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "="
    };
    String[] simbolosDireita = {"÷", "×", "-", "+", "="};
    String[] simbolosCima = {"AC", "+/-", "%"};

    JFrame frame = new JFrame("Calculadora");
    JLabel rótuloExibição = new JLabel();
    JPanel painelExibição = new JPanel();
    JPanel botõesExibição = new JPanel();

    // Variáveis que vão rastrear os dois numeros e o operador
    String a = "0";
    String operador = null;
    String b = null;

    Calculadora(){
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // centralizar a janela
        frame.setResizable(false); // "false" para o usuário não alterar o tamanho da janela
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // adiciona o botão para sair
        frame.setLayout(new BorderLayout()); // layout de borda

        rótuloExibição.setBackground(preto);
        rótuloExibição.setForeground(Color.white); // cor do texto
        rótuloExibição.setFont(new Font("Arial", Font.PLAIN, 80));
        rótuloExibição.setHorizontalAlignment(JLabel.RIGHT); // exibe o texto a direita
        rótuloExibição.setText("0");
        rótuloExibição.setOpaque(true);

        painelExibição.setLayout(new BorderLayout());
        painelExibição.add(rótuloExibição); // coloca o rótulo de texto dentro do painel
        frame.add(rótuloExibição, BorderLayout.NORTH); // coloca o painel dentro da janela / coloca o painel de exibição no topo da janela

        botõesExibição.setLayout(new GridLayout(5, 4));
        botõesExibição.setBackground(preto);
        frame.add(botõesExibição); // adiciona o quadro ao painel de botões

        for (int count = 0 ; count < botõesValores.length; count++){
            JButton botão = new JButton();
            String valorBotão = botõesValores[count];
            botão.setFont(new Font("Arial", Font.PLAIN, 30));
            botão.setText(valorBotão);
            botão.setFocusable(false); // remove o retangulo ao clical no botão 
            botão.setBorder(new LineBorder(preto));
            if (Arrays.asList(simbolosCima).contains(valorBotão)){
                botão.setBackground(cinzaClaro);
                botão.setForeground(preto);
            } else if (Arrays.asList(simbolosDireita).contains(valorBotão)){
                botão.setBackground(laranja);
                botão.setForeground(Color.white);
            } else {
                botão.setBackground(cinzaEscuro);
                botão.setForeground(Color.white);
            }
            botõesExibição.add(botão); 

            botão.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    JButton botão = (JButton) e.getSource();
                    String valorBotão = botão.getText();
                    if (Arrays.asList(simbolosDireita).contains(valorBotão)){
                        if (valorBotão == "=") {
                            if (a != null) {
                                b = rótuloExibição.getText();
                                double numA = Double.parseDouble(a);
                                double numB = Double.parseDouble(b);

                                if (operador == "+") {
                                    rótuloExibição.setText(removerZeroDecimal(numA+numB));
                                }
                                else if (operador == "-") {
                                    rótuloExibição.setText(removerZeroDecimal(numA-numB));
                                }
                                else if (operador == "×") {
                                    rótuloExibição.setText(removerZeroDecimal(numA*numB));
                                }
                                else if (operador == "÷") {
                                    rótuloExibição.setText(removerZeroDecimal(numA/numB));
                                }
                                limparTudo();
                            }
                        } else if ("+-×÷".contains(valorBotão)) {
                            if (operador == null) {
                                a = rótuloExibição.getText();
                                rótuloExibição.setText("0");
                                b = "0";
                            }
                            operador = valorBotão;
                        }
                    } else if (Arrays.asList(simbolosCima).contains(valorBotão)) {
                        if (valorBotão == "AC") {
                            limparTudo();
                            rótuloExibição.setText("0");
                        } else if (valorBotão == "+/-") {
                            double numeroNeg = Double.parseDouble(rótuloExibição.getText());
                            numeroNeg *= -1;
                            rótuloExibição.setText(removerZeroDecimal(numeroNeg));
                        } else if (valorBotão == "%") {
                            double numeroNeg = Double.parseDouble(rótuloExibição.getText());
                            numeroNeg /= 100 ;
                            rótuloExibição.setText(removerZeroDecimal(numeroNeg));
                        }
                    } else {
                        if (valorBotão == "."){
                            if (!rótuloExibição.getText().contains(valorBotão)) {
                                rótuloExibição.setText(rótuloExibição.getText() + valorBotão ); 
                            }
                        } else if ("0123456789".contains(valorBotão)) {
                            if (rótuloExibição.getText() == "0"){
                                rótuloExibição.setText(valorBotão); // em vez de ser 05 vai ser 5
                            } else {
                                rótuloExibição.setText(rótuloExibição.getText() + valorBotão );
                            }
                        }
                    }   
                }
            });
        }
    }

    public void limparTudo(){
        a = "0";
        operador = null;
        b = null;
    }

    String removerZeroDecimal(double numeroNeg) {
        if (numeroNeg % 1 == 0){
            return Integer.toString((int) numeroNeg);
        }
        return Double.toString(numeroNeg);
    }

}
