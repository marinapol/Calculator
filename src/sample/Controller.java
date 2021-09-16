package sample;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class Controller {

    private String result = "";
    private String num = "";
    private String lastSign = null;


    @FXML
    private TextField tf;
    @FXML
    private Button one;
    @FXML
    private Button two;
    @FXML
    private Button three;
    @FXML
    private Button four;
    @FXML
    private Button five;
    @FXML
    private Button six;
    @FXML
    private Button seven;
    @FXML
    private Button eight;
    @FXML
    private Button nine;
    @FXML
    private Button zero;
    @FXML
    private Button dot;
    @FXML
    private Button sign;
    @FXML
    private Button plus;
    @FXML
    private Button minus;
    @FXML
    private Button mul;
    @FXML
    private Button div;
    @FXML
    private Button sqrt;
    @FXML
    private Button pow;
    @FXML
    private Button clean;
    @FXML
    private Button equals;


    public void pressKey(KeyEvent keyEvent) {
        KeyCode keyCode = keyEvent.getCode();
        switch (keyCode) {
            case DIGIT0:
            case NUMPAD0:
                zero.fire();
                break;
            case DIGIT1:
            case NUMPAD1:
                one.fire();
                break;
            case DIGIT2:
            case NUMPAD2:
                two.fire();
                break;
            case DIGIT3:
            case NUMPAD3:
                three.fire();
                break;
            case DIGIT4:
            case NUMPAD4:
                four.fire();
                break;
            case DIGIT5:
            case NUMPAD5:
                five.fire();
                break;
            case DIGIT6:
            case NUMPAD6:
                six.fire();
                break;
            case DIGIT7:
            case NUMPAD7:
                seven.fire();
                break;
            case DIGIT8:
            case NUMPAD8:
                eight.fire();
                break;
            case DIGIT9:
            case NUMPAD9:
                nine.fire();
                break;
            case DELETE:
                clean.fire();
                break;
            case ADD:
                plus.fire();
                break;
            case SUBTRACT:
                minus.fire();
                break;
            case MULTIPLY:
                mul.fire();
                break;
            case DIVIDE:
                div.fire();
                break;
            case DECIMAL:
            case PERIOD:
                dot.fire();
                break;
            case ENTER:
                equals.fire();
                break;
            default:
                Alert a = new Alert(Alert.AlertType.ERROR, "Некорретктный ввод");
                a.showAndWait();
                break;
        }
    }

    public void pressButton(ActionEvent actionEvent) {
        Button pressedButton = (Button) actionEvent.getSource();
        switch (pressedButton.getId()) {
            case "one":
                num += "1";
                tf.appendText("1");
                break;
            case "two":
                num += "2";
                tf.appendText("2");
                break;
            case "three":
                num += "3";
                tf.appendText("3");
                break;
            case "four":
                num += "4";
                tf.appendText("4");
                break;
            case "five":
                num += "5";
                tf.appendText("5");
                break;
            case "six":
                num += "6";
                tf.appendText("6");
                break;
            case "seven":
                num += "7";
                tf.appendText("7");
                break;
            case "eight":
                num += "8";
                tf.appendText("8");
                break;
            case "nine":
                num += "9";
                tf.appendText("9");
                break;
            case "zero":
                if(!num.contains(".") && num.startsWith("0"))
                    break;
                num += "0";
                tf.appendText("0");
                break;
            case "dot":
                if(num.equals("")){
                    num += "0.";
                    tf.appendText("0.");
                }
                else if(!num.contains(".")){
                    num += ".";
                    tf.appendText(".");
                }
                break;

            case "sign":
                if(num.equals("")) {
                    result = calc(result,"-1","mul");
                    tf.setText(String.valueOf(result));
                    addSymb(lastSign);
                }
                else {
                    num = calc(num,"-1", "mul");
                    if(!result.equals("")) {
                        tf.setText(result);
                        addSymb(lastSign);
                        tf.appendText("(" + num +")");
                    }
                    else tf.setText(num);
                }
                break;


            case "plus":
            case "minus":
            case "mul":
            case "div":
            case "pow":
            case "equals":
            case "sqrt":
                processing(pressedButton.getId());
                break;


            case "clean":
                tf.setText("");
                result = "";
                num="";
                lastSign = null;
                break;
        }
    }

    private void processing(String oper) {
        if(oper.equals("equals")){
            num = calc(result,num,lastSign);
            result = "";
            lastSign = null;
            tf.setText(num);
            return;
        }
        if(oper.equals("sqrt")){
            Alert sqrtOfNegativeNumAlert = new Alert(Alert.AlertType.ERROR, "Нельзя извлечь корень из отрицательного числа!");
            if(!num.equals("")){
                if(Double.parseDouble(num)<0) {
                    sqrtOfNegativeNumAlert.showAndWait();
                    return;
                }
                num = String.valueOf(Math.sqrt(Double.parseDouble(num)));
                tf.setText("");
                if(!result.equals(""))
                    tf.appendText(result);
                if(lastSign!=null)
                    addSymb(lastSign);
                tf.appendText(num);
            } else if(!result.equals("")){
                if(Double.parseDouble(result)<0) {
                    sqrtOfNegativeNumAlert.showAndWait();
                    return;
                }
                num = String.valueOf(Math.sqrt(Double.parseDouble(result)));
                tf.setText(num);
                lastSign = null;
            }
            return;
        }
        if(num.equals("") && !result.equals("")){
            lastSign = oper;
            tf.setText(result);
            addSymb(lastSign);
            return;
        }
        if(lastSign == null){
            result = num;
            lastSign = oper;
        }
        else{
            result = calc(result,num,lastSign);
            lastSign = oper;
            tf.setText(result);
        }
        num="";
        addSymb(oper);

    }

    private String calc(String a, String b, String oper){
        try {
            switch (oper) {
                case "plus":
                    return String.valueOf(Double.parseDouble(a) + Double.parseDouble(b));
                case "minus":
                    return String.valueOf(Double.parseDouble(a) - Double.parseDouble(b));
                case "mul":
                    return String.valueOf(Double.parseDouble(a) * Double.parseDouble(b));
                case "div":
                    if(Double.parseDouble(b) == 0){
                        Alert zeroDivAlert  = new Alert(Alert.AlertType.ERROR, "Делить на 0 нельзя!");
                        zeroDivAlert.showAndWait();
                        break;
                    }

                    return String.valueOf(Double.parseDouble(a) / Double.parseDouble(b));
                case "pow":
                    return  String.valueOf(Math.pow(Double.parseDouble(a), Double.parseDouble(b)));
            }
        }
        catch (Exception ignored){}
        return "";
    }
    private void addSymb(String sign){
        switch (sign){
            case "plus":
                tf.appendText("+");
                break;
            case "minus":
                tf.appendText("-");
                break;
            case "mul":
                tf.appendText("*");
                break;
            case "div":
                tf.appendText("/");
                break;
            case "pow":
                tf.appendText("^");
                break;
        }
    }
}




