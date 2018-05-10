/*
* GROUP MEMEBERS: Theelipan Prabakar, James Carr, Usman
* ----------------------------------------------------------------------------------------------
* 8 + 8 works, 8 +8 works, but 8+ 8 does not. (+ can be replaced by -/*()) 
* Need to give the cards value numbers, so can check if the user is using the card values and not random numbers to add to 24 
*Need a way to find the correct solution to the problem and print it out to the user, Add a button that says show answer or something like that
* Make sure the user uses each card once and only once. 
*-----------------------------------------------------------------------------------------------
*/

import java.util.Stack;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {

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
	private ImageView imgCard1;

	@FXML
	private ImageView imgCard2;

	@FXML
	private ImageView imgCard3;

	@FXML
	private ImageView imgCard4;
	

    @FXML
    void refresh(ActionEvent event) {
		cards();
		myTextField.clear();
    }
    
	@FXML
	void startGame(ActionEvent event) {
		cards();
		BtnStartGame.setVisible(false);
	}

	@FXML
	void verify(ActionEvent event) {
		verifyCards();
	}

	@FXML
	void textField(ActionEvent event) {

	}

	public void cards() {

		boolean[] randomCards = new boolean[52];

		// choose 4 random distinct cards from the deck
		int count = 0;

		while (count < 4) {// Display only four cards

			int card1 = (int) (Math.random() * 12) + 1;// Ignore zero
			int card2 = (int) (Math.random() * 12) + 1;// Ignore zero
			int card3 = (int) (Math.random() * 12) + 1;// Ignore zero
			int card4 = (int) (Math.random() * 12) + 1;// Ignore zero

			if ((randomCards[card1] = !randomCards[card2]) && (randomCards[card1] = !randomCards[card3])
					&& (randomCards[card1] = !randomCards[card4]) && (randomCards[card2] = !randomCards[card3])
					&& (randomCards[card2] = !randomCards[card4]) && (randomCards[card3] = !randomCards[card4])) {

				String[] cards = { "clubs", "diamonds", "hearts", "spades" };

				String name1 = cards[(int) (Math.random() * cards.length)];
				String name2 = cards[(int) (Math.random() * cards.length)];
				String name3 = cards[(int) (Math.random() * cards.length)];
				String name4 = cards[(int) (Math.random() * cards.length)];

				randomCards[card1] = true;

				Image image1 = new Image("png/" + (card1 + 1) + "_of_" + name1 + ".png");
				Image image2 = new Image("png/" + (card2 + 1) + "_of_" + name2 + ".png");
				Image image3 = new Image("png/" + (card3 + 1) + "_of_" + name3 + ".png");
				Image image4 = new Image("png/" + (card4 + 1) + "_of_" + name4 + ".png");

				imgCard1.setImage(image1);
				imgCard2.setImage(image2);
				imgCard3.setImage(image3);
				imgCard4.setImage(image4);

				int value = card1 % 13;

				validNumbers[count] = (value == 0) ? 13 : value;
				count++;

			}

		}

	}

	public void verifyCards() {

		String text = myTextField.getText();
		char[] chars = text.toCharArray();

		// Set up Stack for numbers
		Stack<Integer> nums = new Stack<Integer>();

		// Set up Stack for Operators
		Stack<Character> ops = new Stack<Character>();

		for (int i = 0; i < chars.length; i++) {
			if (chars[i] == ' ') {
				continue;
			}
			// Do the stack for numbers, if its 0 - 9, means its a number
			if (chars[i] >= '0' && chars[i] <= '9') {
				StringBuffer sbuf = new StringBuffer();

				// If there is more than one digit in a number, check, then push if so
				while (i < chars.length && chars[i] >= '0' && chars[i] <= '9') {
					sbuf.append(chars[i++]);
					nums.push(Integer.parseInt(sbuf.toString()));
				}
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
			 * ------------------------------------------------------------------------
			 *  The char is a operator +, -, /, * , so push it to operator stack While top of
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

		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining nums
		while (!ops.empty())
			nums.push(operatorCases(ops.pop(), nums.pop(), nums.pop()));

		// Result is on the top of the numbs Stack so Return the Top of number stack
		// Check to see if the number is equal to 24
		int result = nums.pop();
		if (result != 24) {
			System.out.println("wrong " + result + " is NOT equal to 24");
		} else
			System.out.println("Correct " + result + " is equal to 24");
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

} // end brace
