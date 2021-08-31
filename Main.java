import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//TODO: Please Ignore TODO notes:

public class Main extends JFrame {
    private JPanel rootPanel;
    private JTextField userName;
    private JButton LogIn;
    private JList FriendList;
    private JList SuggestedList;
    private JLabel information;
    //additional features
    private JTextField friendName;
    private JButton AddFriend;
    private JLabel addFriendLabel;

    private String currentUser;
    private int currentID;
    private SocialNetwork friendsNetwork;

    //TODO: CTRL + CLICK and arrows to navigate function methods/source
//TODO: Use assessment pdf help guide**

    public Main() {
        //Load the data
        friendsNetwork = new SocialNetwork();
        friendsNetwork.Load();

        LogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentUser = userName.getText();
                currentID = friendsNetwork.FindUserID(currentUser);
                if (currentID == -1){
                    information.setText("User Information: Unknown");
                } else {
                    information.setText("User Information: User Name:"+currentUser+" User ID:"+ currentID);
                    String[] friends = friendsNetwork.GetMyFriends(currentUser);
                    System.out.println("END of GetMyFriends:" + java.time.LocalDateTime.now());

                    FriendList.setListData(friends);

                    String[] recommendations = friendsNetwork.GetRecommended(currentUser);
                    SuggestedList.setListData(recommendations);
                }

            }
        });

        //Add friends
        addFriendListener();

        //Clear the input field
        userName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                userName.setText("");
            }
        });

    }

    private void addFriendListener() {
        AddFriend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String friendUser = friendName.getText();
                int friendID = friendsNetwork.FindUserID(friendUser);

                if (friendID == -1) {
                    addFriendLabel.setText("User Information: Unknown");
                } else {
                    friendsNetwork.addFriend(currentUser, friendID);
                    //Update friends and recommendation lists
                    addFriendLabel.setText("Add a recommended friend");
                    String[] friends = friendsNetwork.GetMyFriends(currentUser);
                    System.out.println("END of GetMyFriends when Adding a friend:" + java.time.LocalDateTime.now());

                    FriendList.setListData(friends);

                    String[] recommendations = friendsNetwork.GetRecommended(currentUser);
                    SuggestedList.setListData(recommendations);
                }

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }


    private void createUIComponents() {
        // TODO: place custom component creation code here

    }
}
