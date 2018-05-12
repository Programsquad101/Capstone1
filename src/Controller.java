

import java.util.Stack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Controller {

	private static final int card1 = 0;

	int[] validNumbers = new int[4];

	@FXML
	private Button btnVerify;

	@FXML
	private Button btnRefresh;

	@FXML
	private Button BtnStartGame;

	@FXML
	private TextField myTextField;
	
	@FXML
	private TextField setText;

	@FXML
	private ImageView imgCard1;

	@FXML
	private ImageView imgCard2;

	@FXML
	private ImageView imgCard3;

	@FXML
	private ImageView imgCard4;
	
	@FXML
	private Pane paneTest;
	
	@FXML
	private TextField tutTxtFld;

	@FXML
	void refresh(ActionEvent event) {
		cards();
		myTextField.clear();
		btnRefresh.setText("Refresh");
		paneTest.setVisible(false);

	}

	/**
	 * @param none
	 * @return void
	 * 
	 */
	@FXML
	void startGame(ActionEvent event) {
		cards();
		BtnStartGame.setVisible(false);
		verifyCards();
	}

	@FXML
	void verify(ActionEvent event) {
		verifyCards();
	}

	@FXML
	void textField(ActionEvent event) {

	}

	/**
	 * @param none
	 * @return void
	 * 
	 */

	public void cards() {

		boolean[] randomCards = new boolean[52];

		// choose 4 random distinct cards from the deck
		int count = 0;
		int card1 = 0;
		int card2 = 0;
		int card3 = 0;
		int card4 = 0;

		while (count < 4) {// Display only four cards

			card1 = ((int) (Math.random() * 12) + 1) + 1;// Ignore zero
			card2 = ((int) (Math.random() * 12) + 1) + 1;// Ignore zero
			card3 = ((int) (Math.random() * 12) + 1) + 1;// Ignore zero
			card4 = ((int) (Math.random() * 12) + 1) + 1;// Ignore zero

			if ((randomCards[card1] = !randomCards[card2]) && (randomCards[card1] = !randomCards[card3])
					&& (randomCards[card1] = !randomCards[card4]) && (randomCards[card2] = !randomCards[card3])
					&& (randomCards[card2] = !randomCards[card4]) && (randomCards[card3] = !randomCards[card4])) {

				String[] cards = { "clubs", "diamonds", "hearts", "spades" };

				String name1 = cards[(int) (Math.random() * cards.length)];
				String name2 = cards[(int) (Math.random() * cards.length)];
				String name3 = cards[(int) (Math.random() * cards.length)];
				String name4 = cards[(int) (Math.random() * cards.length)];

				Image image1 = new Image("png/" + (card1) + "_of_" + name1 + ".png");
				Image image2 = new Image("png/" + (card2) + "_of_" + name2 + ".png");
				Image image3 = new Image("png/" + (card3) + "_of_" + name3 + ".png");
				Image image4 = new Image("png/" + (card4) + "_of_" + name4 + ".png");

				imgCard1.setImage(image1);
				imgCard2.setImage(image2);
				imgCard3.setImage(image3);
				imgCard4.setImage(image4);

				count++;

			}

		}

		String cardOneValue = Integer.toString(card1);
		String cardTwoValue = Integer.toString(card2);
		String cardThreeValue = Integer.toString(card3);
		String cardFourValue = Integer.toString(card4);
		System.out.println(cardOneValue + " - " + cardTwoValue + " - " + cardThreeValue + " - " + cardFourValue);
		setText.setText(cardOneValue +" "+ cardTwoValue +" "+ cardThreeValue +" "+ cardFourValue);

	}

	public void verifyCards() {
		String text = myTextField.getText();
		String settext = setText.getText();
		
		Stack<Integer> cardNums = new Stack<Integer>();
		Stack<Integer>tmpCard = new Stack<Integer>(); 
		
		char[] cardChars = settext.toCharArray();
		
		
		for (int i = 0; i < cardChars.length; i++) {
			if (cardChars[i] >= '1' && cardChars[i] <= '9') {
				StringBuffer sbuf = new StringBuffer();
				
				// If there is more than one digit in a number, check, then push if so
				while (i < cardChars.length && cardChars[i] >= '0' && cardChars[i] <= '9') {
					sbuf.append(cardChars[i++]);
					cardNums.push(Integer.parseInt(sbuf.toString()));
				}
				tmpCard.addAll(cardNums);
				System.out.println(cardNums.peek()); 
				System.out.println(tmpCard.peek()); 
				}
			}
			
		char[] chars = text.toCharArray();
		
		
		myTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, 
                String newValue) {
                if (!newValue.matches("\\d*")) {
                	myTextField.setText(newValue.replaceAll("[^\\d\\h+-/*]", ""));
                }
            }
        });
        

		// Set up Stack for numbers
		Stack<Integer> nums = new Stack<Integer>();
		Stack<Integer>tmpNums = new Stack<Integer>(); 


		// Set up Stack for Operators
		Stack<Character> ops = new Stack<Character>();

		for (int i = 0; i < chars.length; i++) {

			// Do the stack for numbers, if its 0 - 9, means its a number
			if (chars[i] >= '1' && chars[i] <= '9') {
				StringBuffer sbuf = new StringBuffer();
				
				// If there is more than one digit in a number, check, then push if so
				while (i < chars.length && chars[i] >= '0' && chars[i] <= '9') {
					sbuf.append(chars[i++]);
					nums.push(Integer.parseInt(sbuf.toString()));
				}
				tmpNums.addAll(nums);
				System.out.println(nums.peek());
				System.out.println(tmpNums.peek());
			}
			
			// open brace, pushed to operator stack
			else if (chars[i] == '(')
				ops.push(chars[i]);

			// Closing brace, now solve
			else if (chars[i] == ')') {
				// look at the top of the stack, see if there was a ( brace before the ) brace
				while (ops.peek() != '(')
					nums.push(operatorCases(ops.pop(), nums.pop(), nums.pop()));
				ops.pop();
			}

			/*
			 * ------------------------------------------------------------------------ The
			 * char is a operator +, -, /, * , so push it to operator stack While top of
			 * 'ops' has same or greater precedence to current token, which is an operator.
			 * Apply operator on top of 'ops' to top two elements in nums stack
			 * ------------------------------------------------------------------------
			 */
			else if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/') {

				while (!ops.empty() && precedenceOrder(chars[i], ops.peek()))
					nums.push(operatorCases(ops.pop(), nums.pop(), nums.pop()));
				ops.push(chars[i]);
			}
			
		}
	   
		if(tmpNums.peek() == tmpCard.peek()) {
			System.out.println("TEST GOOD"); 
			System.out.println("TMP NUMS" + tmpNums.peek()); 
			System.out.println("TMP cards" + tmpCard.peek()); 

		}else {
			System.out.println("NOPE");
			System.out.println("TMP NUMS" + tmpNums.peek()); 
			System.out.println("TMP cards" + tmpCard.peek()); 
		}

		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining nums
		while (!ops.empty())
			nums.push(operatorCases(ops.pop(), nums.pop(), nums.pop()));

		// Result is on the top of the numbs Stack so Return the Top of number stack
		// Check to see if the number is equal to 24
		int result = nums.pop();
		if (result != 24) {
			System.out.println("Wrong : " + result + " is NOT equal to 24");
		} else
			System.out.println("Correct : " + result + " is equal to 24");
	}

	/*
	 * ------------------------------------------------------------------------
	 * Returns true if 'op2' has higher or same precedence as 'op1', otherwise
	 * returns false.
	 * ------------------------------------------------------------------------
	 */
	public static boolean precedenceOrder(char op1, char op2) {
		if (op2 == '(' || op2 == ')')
			return false;
		// if the second operator is a + or - than it does not get higher precendence
		// over the * and /
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
			return false;
		else
			return true;
	}

	/*
	 * ------------------------------------------------------------------------ A
	 * utility method to apply an operator 'op' on operands 'a' and 'b'. Return the
	 * result.
	 * ------------------------------------------------------------------------
	 */

	public static int operatorCases(char op, int x, int y) {
		switch (op) {
		case '+':
			return y + x;
		case '-':
			return y - x;
		case '/':
			// not allowed to divide by 0, throw the exceptions
			if (x == 0)
				throw new UnsupportedOperationException("Cannot divide by zero");
			return y / x;
		case '*':
			return y * x;
		}
		return 0;
	}

} // end br