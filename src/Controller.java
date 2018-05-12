

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
	private Button BtnSolution; 

	@FXML
	private TextField myTextField;
	
	@FXML
	private TextField txtSolution;
	
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
	private TextField txtFldAns;

	@FXML
	void refresh(ActionEvent event) {
		cards();
		myTextField.clear();
		btnRefresh.setText("Refresh");
		paneTest.setVisible(false);

	}

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
	
	@FXML
	void findSolution(ActionEvent event) {
		solution(); 
	}


	/**
	 * This method, selects 4 random cards and displays on the screen
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
		
		int solution = 0; 
		if(solution!=24) {
		for(int limit = 0; limit < 25; limit++) {
		
				System.out.println("No");
			}
		}
		System.out.println(solution);
		
	}
	
	/**
	 * Find the solution to the probelm, when button is clicked. 
	 */
	public void solution() {
		
	}

	/**
	 * This method takes the cards that the user entered and stores it into its own stack. Then compares it to the cards
	 * that are being displayed. Verifies that everything the user is correct, and if they got the answer
	 */
	public void verifyCards() {
		
		String text = myTextField.getText();
		String settext = setText.getText();
		
		// Stack for cards
		Stack<Integer> cardNums = new Stack<Integer>();
		Stack<Integer>tmpCard = new Stack<Integer>(); 
		// Set up Stack for numbers
		Stack<Integer> nums = new Stack<Integer>();
		Stack<Integer>tmpNums = new Stack<Integer>(); 

		// Set up Stack for Operators
		Stack<Character> ops = new Stack<Character>();

		
		char[] cardChars = settext.toCharArray();
		
		
		for (int i = 0; i < cardChars.length; i++) {
			if (cardChars[i] >= '1' && cardChars[i] <= '9') {
				StringBuffer sbuf = new StringBuffer();
				
				// If there is more than one digit in a number, check, then push if so
				while (i < cardChars.length && cardChars[i] >= '0' && cardChars[i] <= '9') {
					sbuf.append(cardChars[i++]);
					cardNums.push(Integer.parseInt(sbuf.toString()));
					tmpCard.push(Integer.parseInt(sbuf.toString()));
				}
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
        

		for (int i = 0; i < chars.length; i++) {

			// Do the stack for numbers, if its 0 - 9, means its a number
			if (chars[i] >= '1' && chars[i] <= '9') {
				StringBuffer sbuf = new StringBuffer();
				
				// If there is more than one digit in a number, check, then push if so
				while (i < chars.length && chars[i] >= '0' && chars[i] <= '9') {
					sbuf.append(chars[i++]);
					nums.push(Integer.parseInt(sbuf.toString()));
					tmpNums.push(Integer.parseInt(sbuf.toString()));

				}
				System.out.println(nums.peek());
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

		
			else if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/') {

				while (!ops.empty() && precedenceOrder(chars[i], ops.peek()))
					nums.push(operatorCases(ops.pop(), nums.pop(), nums.pop()));
				ops.push(chars[i]);
			}
			
		}
	   
		//  pop out the tmp values, then check to see if they equal eachother for validation.

		
int cardP1 = tmpCard.pop(); 
int cardP2 = tmpCard.pop(); 
int cardP3 = tmpCard.pop(); 
int cardP4 = tmpCard.pop(); 


int numP1 = tmpNums.pop(); 
int numP2 = tmpNums.pop(); 
int numP3 = tmpNums.pop(); 
int numP4 = tmpNums.pop(); 
int whileLoopGood = 0; 
boolean resultWhile;
resultWhile = false;
while(!resultWhile) { 
	while(numP1 == cardP1 || numP1 == cardP2 || numP1 == cardP3 || numP1 == cardP4) {
		while(numP2 == cardP1 || numP2 == cardP2 || numP2 == cardP3 || numP2 == cardP4) {
			while(numP3 == cardP1 || numP3 == cardP2 || numP3 == cardP3 || numP3 == cardP4) {
				while(numP4 == cardP1 || numP4 == cardP2 || numP4 == cardP3 || numP4 == cardP4) {
					whileLoopGood = 1;
					resultWhile = true; 
					break;
				}
				break;
			}
			break; 
		}
		break;
	}
	break;
}

//if the while loop does reach the final desitintion then user did not type in correctly. 



		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining nums
		while (!ops.empty())
			nums.push(operatorCases(ops.pop(), nums.pop(), nums.pop()));

		// Result is on the top of the numbs Stack so Return the Top of number stack
		// Check to see if the number is equal to 24
		int result = nums.pop();
		if (result != 24) {
			txtFldAns.setText("Wrong : " + result + " is NOT equal to 24");
			if(whileLoopGood == 0) {
				txtFldAns.setText("You must use all 4 cards Once and only once");
				}
		} else
			txtFldAns.setText("Correct : " + result + " is equal to 24");
		if(whileLoopGood == 0) {
			txtFldAns.setText("You must use all 4 cards Once and only once");
			}
	}

	/**
	 * This method takes in the operators being used and comapres them to eachother, in order to 
	 * find the order in which they should be used. It takes from the stack that the operators are stored in
	 * @param op1
	 * @param op2
	 * @return boolean
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

	
	/**
	 * Returns the result of the math. Each operator is applied to what math should be used for it. Does not allow to divide by 0
	 * @param op
	 * @param x
	 * @param y
	 * @return int
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