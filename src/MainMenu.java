import javax.swing.*; 
import java.awt.*; 
public class MainMenu{

private static JFrame rootFrame;
private static JTabbedPane tabbedPane = new JTabbedPane();
private static JPanel resourcePanel = new JPanel();
private static JPanel userPanel = new JPanel(); 
private static JPanel loanPanel = new JPanel();

   public static enum PANEL {
      USER,
      RESOURCE,
      LOAN
   }
   /*one row , three columns */
   private static final GridLayout format = new GridLayout(1,4); 
   public MainMenu(String title){
      
      rootFrame = new JFrame(title);
      rootFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
      rootFrame.add(tabbedPane);
      
      loanPanel.setLayout(format);
      resourcePanel.setLayout(format);
      userPanel.setLayout(format);

      tabbedPane.add("Loans",loanPanel); 
      tabbedPane.add("Resources",resourcePanel);
      tabbedPane.add("Users",userPanel); 

      init(); 
   }
   /*add buttons and add eventListeners */
   private void init(){
   /*handle loan ui*/
      JButton addLoan = new JButton("Create Loan");
      JButton delLoan = new JButton("End Loan"); 
      JButton listLoans = new JButton("List Loans");
      JTextArea loanOutput = new JTextArea(); 
      JScrollPane lOut = new JScrollPane(loanOutput);       
      /*set addLoan button to create a pop up once clicked */
      /*addLoan action listener will echo results to text area */
      addLoan.addActionListener(event->{
         String targetResourceID = (String) JOptionPane.showInputDialog(
               "enter the resource's ID"
            ); 
         String targetUserID = (String) JOptionPane.showInputDialog(
               "enter the user's ID"
            );
         final String  durations[] = {"WEEK","FORTNIGHT","MONTH"};
         String targetDuration = (String) JOptionPane.showInputDialog(
               rootFrame,
               "please enter the loan duration",
               "Prompt",
               JOptionPane.PLAIN_MESSAGE,
               null,
               durations,
               "WEEK"
            );
         String args[] = {targetResourceID.trim()
            ,targetUserID.trim(),targetDuration
         };
         LibManagerJob addLoanJob = new LibManagerJob(
               LibManagerJob.CMD.ADD_LOAN,
               loanOutput,
               args);

         try{
            addLoanJob.execute(); 
         }catch(Exception e){
         e.printStackTrace(); 
         }
      });
      
      /* bind events for deleteLoan button */
      
      delLoan.addActionListener(event->{
         String targetLoanID = (String) JOptionPane.showInputDialog(
            "Enter the loan's ID"
            );
         String args [] = {targetLoanID.trim()};
         LibManagerJob delLoanJob = new LibManagerJob(
               LibManagerJob.CMD.DELETE_LOAN,
               loanOutput,
               args);

         try{
            delLoanJob.execute(); 
         }catch(Exception e){
            e.printStackTrace(); 
         }
      });
      /*set listLoan button to output to text area */
      listLoans.addActionListener(event->{
            String args [] = {"","",""}; 
            LibManagerJob listLoanJob = new LibManagerJob(
                  LibManagerJob.CMD.LIST_LOAN,
                  loanOutput,
                  args); 
            try{
               listLoanJob.execute(); 
            }catch(Exception e){
               e.printStackTrace(); 
            }

         });
      add(PANEL.LOAN,addLoan);
      add(PANEL.LOAN,delLoan);
      add(PANEL.LOAN,listLoans);
      add(PANEL.LOAN,lOut); 
   /*handle resource ui */
      JButton addResource = new JButton("Add Resource");
      JButton delResource = new JButton("Delete Resource");
      JButton listResources = new JButton("List Resources");
      JTextArea resourceOutput = new JTextArea(); 
      JScrollPane rOut = new JScrollPane(resourceOutput); 
      addResource.addActionListener(event->{
         String name = (String) JOptionPane.showInputDialog(
               "enter the resource's name"
            ); 
         String author = (String) JOptionPane.showInputDialog(
               "enter the name of the author"
            );
         String args[] = {name.trim(),author.trim()};
         LibManagerJob addResourceJob = new LibManagerJob(
               LibManagerJob.CMD.ADD_RESOURCE,
               resourceOutput,
               args);

         try{
            addResourceJob.execute(); 
         }catch(Exception e){
         e.printStackTrace(); 
         }
      });
      
      /* bind events for deleteResource button */
      
      delResource.addActionListener(event->{
         String targetResourceID = (String) JOptionPane.showInputDialog(
            "Enter the resource's ID"
            );
         String args [] = {targetResourceID.trim()};
         LibManagerJob delResourceJob = new LibManagerJob(
               LibManagerJob.CMD.DELETE_RESOURCE,
               resourceOutput,
               args);

         try{
            delResourceJob.execute(); 
         }catch(Exception e){
            e.printStackTrace(); 
         }
      });
      /*set listResource button to output to text area */
      listResources.addActionListener(event->{
            String args [] = {"","",""}; 
            LibManagerJob listResourceJob = new LibManagerJob(
                  LibManagerJob.CMD.LIST_RESOURCE,
                  resourceOutput,
                  args); 
            try{
               listResourceJob.execute(); 
            }catch(Exception e){
               e.printStackTrace(); 
            }

         });

      add(PANEL.RESOURCE,addResource);
      add(PANEL.RESOURCE,delResource);
      add(PANEL.RESOURCE,listResources);
      add(PANEL.RESOURCE,rOut); 
   /*handle user ui*/
      JButton addUser = new JButton("Add User");
      JButton delUser = new JButton("Delete User");
      JButton listUsers = new JButton("List Users"); 
      JTextArea userOutput = new JTextArea(); 
      JScrollPane uOut = new JScrollPane(userOutput); 
      addUser.addActionListener(event->{
         String fName = (String) JOptionPane.showInputDialog(
               "enter first name"
            ); 
         String lName = (String) JOptionPane.showInputDialog(
               "enter last name"
            );
         String args[] = {fName.trim(),lName.trim()};
         LibManagerJob addUserJob = new LibManagerJob(
               LibManagerJob.CMD.ADD_USER,
               userOutput,
               args);
         try{
            addUserJob.execute(); 
         }catch(Exception e){
         e.printStackTrace(); 
         }
      });
      
      /* bind events for deleteLoan button */
      
      delUser.addActionListener(event->{
         String targetUserID = (String) JOptionPane.showInputDialog(
            "Enter the user's ID"
            );
         String args [] = {targetUserID.trim()};
         LibManagerJob delUserJob = new LibManagerJob(
               LibManagerJob.CMD.DELETE_USER,
               userOutput,
               args);

         try{
            delUserJob.execute(); 
         }catch(Exception e){
            e.printStackTrace(); 
         }
      });
      /*set listLoan button to output to text area */
      listUsers.addActionListener(event->{
            String args [] = {"","",""}; 
            LibManagerJob listUserJob = new LibManagerJob(
                  LibManagerJob.CMD.LIST_USER,
                  userOutput,
                  args); 
            try{
               listUserJob.execute(); 
            }catch(Exception e){
               e.printStackTrace(); 
            }

         });

      add(PANEL.USER,addUser);
      add(PANEL.USER,delUser);
      add(PANEL.USER,listUsers);
      add(PANEL.USER,uOut); 
   }
   private void add(PANEL tab , JComponent component){
      
      switch(tab){
         case USER:
            userPanel.add(component); 
            break;
         case RESOURCE:
            resourcePanel.add(component); 
            break;
         case LOAN:
            loanPanel.add(component); 
       
      }
   }
    
   public void show(){
      rootFrame.pack(); 
      rootFrame.setSize(1200,800); 
      rootFrame.setVisible(true);
   }
   public void hide(){
      rootFrame.setVisible(false); 
   }
}
