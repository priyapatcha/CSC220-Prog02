package prog02;

/**
 * A program to query and modify the phone directory stored in csc220.txt.
 *
 * @author vjm
 */
public class Main {

    /**
     * Processes user's commands on a phone directory.
     *
     * @param fn The file containing the phone directory.
     * @param ui The UserInterface object to use
     *           to talk to the user.
     * @param pd The PhoneDirectory object to use
     *           to process the phone directory.
     */
    public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
        pd.loadData(fn);
        boolean changed = false;

        String[] commands = {"Add/Change Entry", "Look Up Entry", "Remove Entry", "Save Directory", "Exit"};

        String name, number, oldNumber;

        while (true) {
            int c = ui.getCommand(commands);
            switch (c) {
                case -1:
                    ui.sendMessage("You shut down the program, restarting.  Use Exit to exit.");
                    break;
                case 0:
                    name = ui.getInfo("Enter the name ");
                    if (name == null || name.length() == 0) {
                        ui.sendMessage("Invalid entry");
                        break;
                    }

                    number = ui.getInfo("Enter the number ");
                    if (number == null) {
                        ui.sendMessage("Invalid entry");
                        break;
                    }
                    oldNumber = pd.addOrChangeEntry(name, number);
                    if (oldNumber == null) {
                        ui.sendMessage("Number for " + name + " was changed \n" + "Old number: " + oldNumber + "\nNew number: " + number);
                        break;
                    } else {
                        ui.sendMessage(name + " was added to the directory\n "  + "\n New number: " + number);
                    }
                    break;
                case 1:
                    name = ui.getInfo("Enter the name ");
                    if (name == null)
                        break;
                    if (name.equals("") || name.length() == 0) {
                        ui.sendMessage("Blank names are not allowed.");
                        break;
                    }

                    number = pd.lookupEntry(name);
                    if (number == null) {
                        ui.sendMessage(name + " is not in the directory.");
                    } else {
                        ui.sendMessage(name + " has phone number " + number);
                    }
                    break;
                case 2:
                    name = ui.getInfo("Enter the name ");
                    if (name == null || name.length() == 0) {
                        break;
                    }
                    pd.removeEntry(name);
                    break;
                case 3:
                    pd.save();
                    break;
                case 4:
                    if(changed) {
                        ui.sendMessage("The directory has been changed. At the next window, select  'Yes' to exit, and 'No' to return to menu.");

                        String[] savePrompts = {"Yes","No"};

                        int a = ui.getCommand(savePrompts);

                        switch (a) {
                            case 0:
                                System.exit(0);
                                break;
                            case 1:
                                break;
                        }
                        break;
                    } else {
                        return;
                    }
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String fn = "csc220.txt";
        //PhoneDirectory pd = new ArrayBasedPD();
        PhoneDirectory pd = new SortedPD();
        //UserInterface ui = new ConsoleUI();

        UserInterface ui = new GUI("Phone Directory");
        //UserInterface ui = new TestUI("Phone Directory");
        processCommands(fn, ui, pd);
    }
}
