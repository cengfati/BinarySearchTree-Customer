package control;


import model.BinarySearchTree;
import model.Customer;
import model.List;
import view.DrawingPanel;
import view.treeView.TreeNode;
import view.treeView.TreePath;

/**
 * Created by Jean-Pierre on 12.01.2017.
 */
public class MainController {

    //Attribute
    private boolean surpriseIsSet;

    //Referenzen
    private BinarySearchTree<Customer> customerTree;

    public MainController(){
        surpriseIsSet = false;
        customerTree = new BinarySearchTree<>();
        createCustomerTree();
    }

    /**
     * Füllt Daten in Form von Kunden-Objekten in den Baum.
     */
    private void createCustomerTree(){
        customerTree.insert(new Customer("Ulf",500));
        customerTree.insert(new Customer("Ralle",250));
        customerTree.insert(new Customer("Bernd",750));
    }

    /**
     * Der Baum wird im übergebenem Panel dargestellt.
     * Dazu wird zunächst die alte Zeichnung entfernt.
     * Im Anschluss wird eine eine internete Hilfsmethode aufgerufen.
     * @param panel Das DrawingPanel-Objekt, auf dem gezeichnet wird.
     */
    public void showTree(DrawingPanel panel){
        panel.removeAllObjects();
        //Der Baum wird in der Mitte des Panels beginnend gezeichnet: panel.getWidth()/2
        //Der linke und rechte Knoten in Tiefe 1 sind jeweils ein Viertel der Breite des Panels entfernt.
        showTree(customerTree, panel, panel.getWidth()/2, 50, panel.getWidth()/4);
    }

    /**
     * Hilfsmethode zum Zeichnen des Baums.
     * Für jeden Knoten wird ein neues TreeNode-Objekt dem panel hinzugefügt.
     * Für jede Kante wird ein neues TreePath-Pbjekt dem panel hinzugefügt.
     * @param tree Der zu zeichnende (Teil)Binärbaum.
     * @param panel Das DrawingPanel-Objekt, auf dem gezeichnet wird.
     * @param startX x-Koordinate seiner Wurzel
     * @param startY y-Koordinate seiner Wurzel
     * @param spaceToTheSide Gibt an, wie weit horizontal entfernt die folgenden Bäume gezeichnet werden soll.
     */
    private void showTree(BinarySearchTree tree, DrawingPanel panel, double startX, double startY, double spaceToTheSide) {
        if (!tree.isEmpty()) {
            TreeNode node = new TreeNode(startX, startY, 10, tree.getContent().toString(), false);
            panel.addObject(node);
            if (!tree.getLeftTree().isEmpty()) {
                TreePath path = new TreePath(startX, startY, startX - spaceToTheSide, startY + 50, 10, false);
                panel.addObject(path);
                showTree(tree.getLeftTree(), panel, startX - spaceToTheSide, startY + 50, spaceToTheSide * 0.5);
            }
            if (!tree.getRightTree().isEmpty()) {
                TreePath path = new TreePath(startX, startY, startX + spaceToTheSide, startY + 50, 10, false);
                panel.addObject(path);
                showTree(tree.getRightTree(), panel, startX + spaceToTheSide, startY + 50, spaceToTheSide * 0.5);
            }
        }
    }

    /**
     * Es wird das Ergebnis einer Traversierung bestimmt.
     * Ruft dazu eine interne Hilfsmethode auf.
     * @return Das Ergebnis der Traversierung als Zeichenkette.
     */
    public String traverse(){
        return traverse(customerTree);
    }

    /**
     * Interne Hilfsmethode zur Traversierung.
     * @param tree Der zu traversierende BinarySearchTree.
     * @return Das Ergebnis der Traversierung als Zeichenkette.
     */
    private String traverse(BinarySearchTree<Customer> tree){
        //TODO 04:  Siehe Rückgabe. You can do it!
        if(!tree.isEmpty()){
            return traverse(tree.getLeftTree()) + traverse(tree.getRightTree()) + " Name: " + tree.getContent().getName();
        }
        return "";
    }

    /**
     * Es wird nach dem letzten Kunden in der Datenmenge gesucht.
     * Falls dieser existiert, wird ein zwei Felder großes Array mit seinem Namen (Index 0) und seinem Umsatz (Index 1) zurückgegeben, sonst null.
     * @return
     */
    public String[] searchLastName(){
        //TODO 05: Umsetzung einer Teilaufgabe einer zurückliegenden Hausaufgabe.
        String[] output = new String[2];

        if(!customerTree.isEmpty()) {
            Customer last = searchLastNameRec(customerTree);

            output[0] = last.getName();
            output[1] = String.valueOf(last.getSales());
        }
        return output;
    }

    private Customer searchLastNameRec(BinarySearchTree<Customer> tree){
        if(tree.getRightTree().isEmpty()){
            return tree.getContent();
        }else{
            return searchLastNameRec(tree.getRightTree());
        }
    }

    /**
     * Bestimme den gesamten Umsatz aller Kunden, die im Baum gespeichert sind.
     * @return Umsatz-Summe
     */
    public int sumUpSales(){
        //TODO 06:  Ein weiterer Algorithmus, der mit einer Traversierung einfach umsetzbar ist.
        return sumUpRec(customerTree);
    }

    private int sumUpRec(BinarySearchTree<Customer> tree){
        if(!tree.isEmpty()){
            return sumUpRec(tree.getLeftTree()) + sumUpRec(tree.getRightTree()) + tree.getContent().getSales();
        }
        return 0;
    }

    /**
     * Fügt dem Baum ein neues Kunden-Objekt hinzu, falls dieses noch nicht existiert.
     * @param name Name des Kunden-Objekts.
     * @param sales Umsatz des Kunden-Objekts.
     * @return true, falls ein neuer Kunde hinzugefügt wurde, sonst false.
     */
    public boolean insert(String name, int sales){
        //TODO 07:  Erste Methode, die auf der Datenstruktur selbst konkret arbeitet und einige Methoden von ihr aufruft.
        Customer customer = new Customer(name,sales);
        if(customerTree.search(customer) == null){
            customerTree.insert(customer);
            return true;
        }
        return false;
    }

    /**
     * Es wird nach einem Kunden gesucht, der den entsprechenden Namen aufweist.
     * Falls einer vorhanden ist, so wird er gelöscht und true zurückgegeben. Sonst wird false zurückgegeben.
     * @param name
     * @return Teilt mit, ob eine Löschung erfolgt ist oder nicht.
     */
    public boolean delete(String name){
        //TODO 08: Methode funktioniert so ähnlich wie die vorherige.
        Customer customer = new Customer(name,0);
        if(customerTree.search(customer) != null){
            customerTree.remove(customerTree.search(customer));
            return true;
        }
        return false;
    }

    /**
     * Es wird im Baum nach einem Kunden mit entsprechendem Namen gesucht.
     * Falls dieser existiert, wird ein zwei Felder großes Array mit seinem Namen (Index 0) und seinem Umsatz (Index 1) zurückgegeben, sonst null.
     * @param name
     * @return
     */
    public String[] searchName(String name){
        //TODO 09: Setze eine Methode zum Suchen eines konkreten Objekts um.
        Customer customer = (customerTree.search(new Customer(name)));
        if(customerTree.search(customer) != null){
            String[] output = new String[2];
            output[0] = customer.getName();
            output[1] = String.valueOf(customer.getSales());
            return output;
        }
        return null;
    }

    /**
     * Es wird im Baum nach einem Kunden gesucht, der den geforderten Umsatz generiert hat.
     * Falls dieser existiert, wird ein zwei Felder großes Array mit seinem Namen (Index 0) und seinem Umsatz (Index 1) zurückgegeben, sonst null.
     * @param sales
     * @return Informationen zum Ausgang der Suche.
     */
    public String[] searchSales(int sales){
        //TODO 10: Diese Suche ist deutlich schwieriger umzusetzen als die vorherige. Welche Schwierigkeit ergibt sich hier?
        Customer customer = searchSalesRec(sales,customerTree);
        if(customer != null){
            String[] output = new String[2];
            output[0] = customer.getName();
            output[1] = String.valueOf(customer.getSales());
            return output;
        }
        return null;
    }

    private Customer searchSalesRec(int sales,BinarySearchTree<Customer> tree){
        if(tree.isEmpty()){
            return null;
        }
        if(tree.getContent().getSales() == sales){
            return tree.getContent();
        }
        Customer cL = searchSalesRec(sales, tree.getLeftTree());
        Customer cR = searchSalesRec(sales, tree.getRightTree());

        if(cL != null){
            return cL;
        }else{
            return cR;
        }
    }

    /**
     * Bestimmt eine Liste von Kunden-Objekten, deren Namen lexikographisch später erscheinen als der Name, der als Parameter übergeben wird.
     * Konvertiert anschließend diese Liste in ein zweidimensionales Zeichenketten-Array. Die erste Dimension bestimmt einen Kunden,
     * die zweite Dimension enthält die Daten "Name" und "Umsatz", also z.B.
     * output[0][0] = "Ulf", output[0][1] = "500"; output[1][0] = "Ralle", output[1][1] = "250" etc.
     * @param name Name, der als lexikographisches Minimum gilt.
     * @return Zweidimensionales Zeichenketten-Array.
     */
    public String[][] listUpperNames(String name){
        //TODO 11: Halbwegs sinnvolle Verknüpfung verschiedener Datenstrukturen zur Übung.

        String[][] output = new String[1][2];
        return output;
    }

    private List listUpperNameRec(BinarySearchTree<Customer> tree, Customer customer){
        if(!tree.isEmpty()){
            return new List<>();
        }else{
            if(tree.getContent().isGreater(customer)){
                List<Customer> list = new List<>();
                list.concat(listUpperNameRec(tree.getLeftTree(),customer));
                list.append(tree.getContent());
                list.concat(listUpperNameRec(tree.getRightTree(),customer));
                return list;
            }else{
                return listUpperNameRec(tree.getRightTree(),customer);
            }
        }
    }

    /**
     * Methode wartet darauf, von der Lehrkraft beschrieben zu werden.
     */
    public void surprise(){
        surpriseIsSet = !surpriseIsSet;
        //TODO 12: "Something big is coming!"
    }
}
