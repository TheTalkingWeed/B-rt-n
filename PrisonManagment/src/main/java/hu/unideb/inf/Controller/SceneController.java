package hu.unideb.inf.Controller;
//3913
import hu.unideb.inf.MainApp;
import hu.unideb.inf.model.LoginPac.JpaLoginDAO;
import hu.unideb.inf.model.LoginPac.Login;
import hu.unideb.inf.model.LoginPac.LoginDAO;
import hu.unideb.inf.model.Prison.JpaPrisonDAO;
import hu.unideb.inf.model.Prison.Prison;
import hu.unideb.inf.model.Prison.PrisonDAO;
import hu.unideb.inf.model.PrisonerPac.JpaPrisonerDAO;
import hu.unideb.inf.model.PrisonerPac.Prisoner;
import hu.unideb.inf.model.PrisonerPac.PrisonerDAO;
import hu.unideb.inf.model.Utils.FileUtils;
import hu.unideb.inf.model.WardenPac.JpaWardenDAO;
import hu.unideb.inf.model.WardenPac.Warden;
import hu.unideb.inf.model.WardenPac.WardenDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.TitledPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class SceneController implements Initializable {

    @FXML
    private TitledPane TitledPaneFIlter;
    @FXML
    private TextField CellNumberFilter;
    @FXML
    private ChoiceBox<String> CrimeFilter;
    @FXML
    private DatePicker EdToFilter;
    @FXML
    private DatePicker EdateFromFilter;
    @FXML
    private TextField FirstNameFilter;
    @FXML
    private TextField LastNameFilter;
    @FXML
    private DatePicker RdateFromFilter;
    @FXML
    private DatePicker RdateToFilter;
    @FXML
    private ChoiceBox<String> SecLvlFilter;
    @FXML
    private ListView<String> LogIn_ListView;
    @FXML
    private ListView<String> LogOut_ListView;
    @FXML
    private ListView<String> Username_ListView;
    @FXML
    private Label loginStatusLabel;
    @FXML
    private TextField PrisonerID;
    @FXML
    private TextField Prisoner_FN;
    @FXML
    private TextField Prisoner_LN;
    @FXML
    private DatePicker EntranceDate;
    @FXML
    private ChoiceBox<String> SecLevel;
    @FXML
    private DatePicker ReleaseDate;
    @FXML
    private TextField Cell_Number;
    @FXML
    private ChoiceBox<String> Crime;
    @FXML
    private TextField WardenID;
    @FXML
    private TextField Warden_FN;
    @FXML
    private ChoiceBox<String> Warden_Floor;
    @FXML
    private DatePicker Warden_JD;
    @FXML
    private TextField Warden_LN;
    @FXML
    private ChoiceBox<String> Warden_Rank;
    @FXML
    private ListView<String> WardenFN_list;
    @FXML
    private ListView<String> WardenFloor_list;
    @FXML
    private ListView<Integer> Wardenid_list;
    @FXML
    private ListView<String> WardenJD_list;
    @FXML
    private ListView<String> WardenLN_list;
    @FXML
    private ListView<String> WardenRank_list;
    @FXML
    private ListView<Integer> PrisonerID_list;
    @FXML
    private ListView<String> PrisonerLN_list;
    @FXML
    private ListView<String> PrisonerRD_list;
    @FXML
    private ListView<String> PrisonerSL_list;
    @FXML
    private ListView<String> PrisonerCrime_list;
    @FXML
    private ListView<String> PrisonerED_list;
    @FXML
    private ListView<String> PrisonerFN_list;
    @FXML
    private ListView<Integer> PrisonerCN_list;
    @FXML
    private Label selectedPrison;
    @FXML
    private TextField FNWardenfilter;
    @FXML
    private TextField LNWardenfilter;
    @FXML
    private DatePicker JoinAfterDate;
    @FXML
    private DatePicker JoinBeforeDate;
    @FXML
    private ChoiceBox<String> RankFilter;
    @FXML
    private ChoiceBox<String> FloorFilter;
    @FXML
    private TitledPane WardenTitledFilter;

    public static Prisoner temp = new Prisoner();
    public static Warden temp2 = new Warden();

    Image editIcon = new Image("/icons/edit.png");
    Image listIcon = new Image("/icons/list_icon.png");
    Image adminRegIcon = new Image("/icons/register_admin.png");


    LoginDAO loginDAO = new JpaLoginDAO();

    List<Integer> pids = new ArrayList<>();
    List<String> fnlist = new ArrayList<>();
    List<String> lnlist = new ArrayList<>();
    List<String> rdlist = new ArrayList<>();
    List<String> edlist = new ArrayList<>();
    List<String> crimelist = new ArrayList<>();
    List<String> seclevellist = new ArrayList<>();
    List<Integer> celnum = new ArrayList<>();

    List<Prisoner> prisoners = new ArrayList<>(getPrisonersWhere());
    List<Warden> wardens = new ArrayList<>(getWardensWhere());

    List<Integer> wardenids = new ArrayList<>();
    List<String> wardenfloors = new ArrayList<>();
    List<String> wardenlns = new ArrayList<>();
    List<String> wardenfns = new ArrayList<>();
    List<String> wardenjds = new ArrayList<>();
    List<String> wardenranks = new ArrayList<>();

    List<Prisoner> forExceloutput = new ArrayList<>();
    List<Warden> WardensforExcelOutput = new ArrayList<>();

    public FileChooser fc = new FileChooser();

    public static int idinforSearch;

    public static int idin;

    public static int wardenIdIn;

    public static int wardenIdforSearch;


    @FXML
    void AdminRegister(ActionEvent event){
        if (LoginController.isAdmin){
            try {
                Stage stage = new Stage();
                Parent root;
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/AdminReg.fxml")));
                Scene scene = new Scene(root);
                stage.setTitle("Register admin");
                stage.getIcons().add(adminRegIcon);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                System.out.println("cant find the fxml ");
            }
        }else {
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);

            alertwindow.setTitle("WARNING!!");
            alertwindow.setContentText("You cannot modify as a Guest");
            alertwindow.showAndWait();
        }
    }

    @FXML
    void DeleteWarden(ActionEvent event) {

        if (LoginController.isAdmin){


            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Delete");
            dialog.setHeaderText("Warden delete");
            dialog.setContentText("Please type the Warden Id:");

            WardenDAO wardenDAO = new JpaWardenDAO();
            List<Warden> wardens = new ArrayList<>(wardenDAO.getWardens());
            Optional<String> result = dialog.showAndWait();
            Warden warden = new Warden();
            boolean found = false;
            String idin;

            if (result.isPresent()) {

                idin = result.get();

                for (Warden w:wardens) {
                    if (w.getUnique_ID() == Integer.parseInt(idin)){
                        found = true;
                        warden = w;
                        break;
                    }
                }
                    if (found) {



                        wardenDAO.deleteWarden(warden);
                        clearListViewWarden();
                        wardens.remove(warden);
                        FillAllListofWarden();
                        updateWardenListView();



                        Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
                        alertwindow.setTitle("Information");
                        alertwindow.setContentText("Delete was successful");
                        alertwindow.showAndWait();
                    }else {
                        Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
                        alertwindow.setTitle("Warning");
                        alertwindow.setContentText("Wrong warden ID");
                        alertwindow.showAndWait();

                    }

            }
        }else {

            Alert alertwindow = new Alert(Alert.AlertType.WARNING);

            alertwindow.setTitle("WARNING!!");
            alertwindow.setContentText("You cannot modify as a Guest");
            alertwindow.showAndWait();
        }
    }

    @FXML
    void SaveWarden(ActionEvent event) {

        if (LoginController.isAdmin){

            try (WardenDAO wdao = new JpaWardenDAO()) {
                if (       WardenID.getText().equals("")
                        || Warden_FN.getText().equals("")
                        || Warden_LN.getText().equals("")
                        || Warden_Rank.getValue() == null
                        || Warden_Floor.getValue() == null
                        || Warden_JD.getValue() == null) {
                    Alert alertwindow = new Alert(Alert.AlertType.WARNING);

                    alertwindow.setTitle("WARNING!!");
                    alertwindow.setContentText("Please fill all fields");
                    alertwindow.showAndWait();

                }else if(isString(WardenID.getText())){
                    Alert alertwindow = new Alert(Alert.AlertType.WARNING);

                    alertwindow.setTitle("WARNING!!");
                    alertwindow.setContentText("WardenID cant be text");
                    alertwindow.showAndWait();

                }else if(containsWardenID(wdao.getWardens(),Integer.parseInt(WardenID.getText())) && !isString(WardenID.getText()) ){
                    Alert alertwindow = new Alert(Alert.AlertType.WARNING);

                    alertwindow.setTitle("WARNING!!");
                    alertwindow.setContentText("WardenID already exists");
                    alertwindow.showAndWait();
                } else {
                    handleWardeData(wdao);

                    Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);

                    alertwindow.setTitle("Information");
                    alertwindow.setContentText("Save was successful");
                    alertwindow.showAndWait();

                    clearWardenInputs();
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }



        } else {

            Alert alertwindow = new Alert(Alert.AlertType.WARNING);

            alertwindow.setTitle("WARNING!!");
            alertwindow.setContentText("You cannot modify as a Guset");
            alertwindow.showAndWait();
        }

    }

    @FXML
    void WardenEdit(ActionEvent event) {
        if (LoginController.isAdmin){
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Edit");
            dialog.setHeaderText("Warden edit");
            dialog.setContentText("Please type the warden's ID:");

            Optional<String> result = dialog.showAndWait();
            WardenDAO wardenDAO = new JpaWardenDAO();
            List <Warden>  wardens = new ArrayList<>(wardenDAO.getWardens());
            boolean found = false;

            if (result.isPresent()){
                wardenIdIn = Integer.parseInt(result.get());
                for (Warden w : wardens)
                {
                    if (w.getUnique_ID() == wardenIdIn){
                        found = true;
                        break;
                    }
                }

            }

            if (found){

                try {
                    Stage stage = new Stage();
                    Parent root;
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/WardenEdit.fxml")));
                    Scene scene = new Scene(root);
                    stage.setTitle("Warden Edit");
                    stage.getIcons().add(editIcon);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("cant find the fxml");
                }

            }else {
                Alert alertwindow = new Alert(Alert.AlertType.WARNING);

                alertwindow.setTitle("WARNING!!");
                alertwindow.setContentText("Warden is not found");
                alertwindow.showAndWait();
            }
        }else{
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);

            alertwindow.setTitle("WARNING!!");
            alertwindow.setContentText("You cannot modify as a Guset");
            alertwindow.showAndWait();
        }
    }

    @FXML
    void WardenSearch(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Search");
        dialog.setHeaderText("Warden search");
        dialog.setContentText("Please type the Id of a warden");
        Optional<String> result;
        result = dialog.showAndWait();
        wardenIdforSearch = Integer.parseInt(result.get());
        boolean bennevan=false;
        if (result.isPresent()) {
            WardenDAO wardenDAO = new JpaWardenDAO();
            List<Warden> wardens = new ArrayList<>(wardenDAO.getWardens());


            for (Warden w:wardens) {
                if (w.getUnique_ID()==Integer.parseInt(result.get())) {
                    temp2 = w;
                    bennevan = true;
                }
            }

            if (bennevan) {
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/WardenInfo.fxml")));
                    Scene scene = new Scene(root);
                    stage.setTitle("Warden information");
                    stage.getIcons().add(listIcon);
                    stage.setScene(scene);
                    stage.show();

                } catch (Exception e) {
                    System.out.println("Cant find the fxml ");
                }

            }else {
                Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
                alertwindow.setTitle("Warning");
                alertwindow.setContentText("Wrong warden ID");
                alertwindow.showAndWait();

            }
        }
    }

    @FXML
    void SaveMP(ActionEvent event) {

        if (LoginController.isAdmin){
            try (PrisonerDAO pdao = new JpaPrisonerDAO())
            {
                


                if (PrisonerID.getText() == null
                    || Prisoner_FN.getText() == null
                    || Prisoner_LN.getText() == null
                    || EntranceDate.getValue()==null
                    || SecLevel.getValue()==null
                    || ReleaseDate.getValue()==null
                    || Cell_Number.getText().equals("")
                    || Crime.getValue()==null){
                    Alert alertwindow = new Alert(Alert.AlertType.WARNING);
                    alertwindow.setTitle("Warning!");
                    alertwindow.setContentText("Please fill all fields");
                    alertwindow.showAndWait();
                }else if (EntranceDate.getValue().compareTo(ReleaseDate.getValue())>0){
                    Alert alertwindow = new Alert(Alert.AlertType.WARNING);
                    alertwindow.setTitle("Warning!");
                    alertwindow.setContentText("Entrance date cannot be after release date!");
                    alertwindow.showAndWait();
                    clearPrisonerInput();
                }else {
                    handlePrisonerData(pdao);

                    Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
                    alertwindow.setTitle("Information");
                    alertwindow.setContentText("Save was successful");
                    alertwindow.showAndWait();
                    clearPrisonerInput();

                }

                
            }catch (Exception e){
                e.printStackTrace();
            }
    } else {

        Alert alertwindow = new Alert(Alert.AlertType.WARNING);

        alertwindow.setTitle("WARNING!!");
        alertwindow.setContentText("You cannot modify as a Guest");
        alertwindow.showAndWait();
    }
    }

    @FXML
    void DeleteButtonMP(ActionEvent event) {
        if (LoginController.isAdmin){

            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Delete");
            dialog.setHeaderText("Prisoner delete");
            dialog.setContentText("Adja meg az ID-ját a Prisonernek.");

            Optional<String> result = dialog.showAndWait();
            PrisonerDAO prisonerDAO = new JpaPrisonerDAO();
            List <Prisoner>  prisoners = new ArrayList<>(prisonerDAO.getPrisoners());
            Prisoner prisoner = new Prisoner();
            boolean found = false;
            String idin;
            if (result.isPresent()){

                idin = result.get();

                for (Prisoner p : prisoners)
                {
                    if (p.getUniqueID() == Integer.parseInt(idin)){
                        found = true;
                        prisoner = p;
                        break;
                    }

                }

                 if (found) {


                     prisonerDAO.deletePrisoner(prisoner);
                     clearListViewPrisoner();
                     prisoners.remove(prisoner);
                     FillAllListofPrisoner();
                     updatePrisonerListView();

                     Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);

                     alertwindow.setTitle("Information");
                     alertwindow.setContentText("Delete was successful");
                     alertwindow.showAndWait();
                 }else{
                     Alert alertwindow = new Alert(Alert.AlertType.WARNING);

                     alertwindow.setTitle("Warning!");
                     alertwindow.setContentText("Wrong prisoner id");
                     alertwindow.showAndWait();
                 }
            }

        } else {

            Alert alertwindow = new Alert(Alert.AlertType.WARNING);

            alertwindow.setTitle("WARNING!!");
            alertwindow.setContentText("You cannot modify as a Guset");
            alertwindow.showAndWait();
        }
    }

    @FXML
    void SearchButtonMP(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Search");
        dialog.setHeaderText("Prisoner search");
        dialog.setContentText("Please type a Prisoner Id:");
        Optional<String> result;
        PrisonerDAO prisonerDAO = new JpaPrisonerDAO();
        result = dialog.showAndWait();
        List<Prisoner> prisoners = new ArrayList<>(prisonerDAO.getPrisoners());
        idinforSearch = Integer.parseInt(result.get());
        boolean bennevan=false;
        if (result.isPresent()) {



            for (Prisoner p:prisoners) {
                if (p.getUniqueID()==idinforSearch) {
                    temp = p;
                    bennevan = true;
                }
            }

                if (bennevan) {
                    try {
                        Stage stage = new Stage();
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Prisonerinfo.fxml")));
                        Scene scene = new Scene(root);
                        stage.setTitle("Prisoner information");
                        stage.getIcons().add(listIcon);
                        stage.setScene(scene);
                        stage.show();

                    } catch (Exception e) {
                        System.out.println("cant find the fxml ");
                    }

                }else {
                    Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);
                    alertwindow.setTitle("Warning");
                    alertwindow.setContentText("Wrong prisoner ID");
                    alertwindow.showAndWait();

                }
        }
    }

    @FXML
    void EditButtonPushed(ActionEvent event){
        if (LoginController.isAdmin){
            TextInputDialog dialog = new TextInputDialog("");
            dialog.setTitle("Edit");
            dialog.setHeaderText("Prisoner delete");
            dialog.setContentText("Please type the prisoner's ID:");

            Optional<String> result = dialog.showAndWait();
            PrisonerDAO prisonerDAO = new JpaPrisonerDAO();
            List <Prisoner>  prisoners = new ArrayList<>(prisonerDAO.getPrisoners());
            boolean found = false;

            if (result.isPresent()){
                idin = Integer.parseInt(result.get());
                for (Prisoner p : prisoners)
                {
                    if (p.getUniqueID() == idin){
                        found = true;
                        break;
                    }
                }

            }

            if (found){

                try {
                    Stage stage = new Stage();
                    Parent root;
                    root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/PrisonerEdit.fxml")));
                    Scene scene = new Scene(root);
                    stage.setTitle("Prisoner information");
                    stage.getIcons().add(editIcon);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    System.out.println("valai nem jo ");
                }

            }else{
                Alert alertwindow = new Alert(Alert.AlertType.WARNING);

                alertwindow.setTitle("WARNING!!");
                alertwindow.setContentText("Prisoner id not found");
                alertwindow.showAndWait();
            }
        }else{
            Alert alertwindow = new Alert(Alert.AlertType.WARNING);

            alertwindow.setTitle("WARNING!!");
            alertwindow.setContentText("You cannot modify as a Guset");
            alertwindow.showAndWait();
        }
    }

    @FXML
    void ListsRefresh(ActionEvent event) {
        clearListViewWarden();
        FillAllListofWarden();
        updateWardenListView();

        clearListViewPrisoner();
        FillAllListofPrisoner();
        updatePrisonerListView();
    }

    @FXML
    void LogOutButtonPressed(ActionEvent event){

        Login login = new Login();

        login.setUsername(LoginController.loggedinuser);
        login.setLogin(LoginController.loginttime);
        login.setLogout(LocalDate.now() + " " + LocalTime.now());


        loginDAO.saveLogin(login);


        Stage toClose = (Stage) loginStatusLabel.getScene().getWindow();
        toClose.close();

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/fxml/LOGIN.fxml"));
            Scene scene;
            scene = new Scene(loader.load());
            stage.setTitle("Prison managment");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
     void SearchByGivenInfo(ActionEvent event) {

        List<Prisoner> forprisonerfilter = new ArrayList<>(prisoners);

        //First name filtering
        if(!"".equals(FirstNameFilter.getText())){


            forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getFname().equals(FirstNameFilter.getText());
                }
            }).collect(Collectors.toList());

        }
        //First name filtering
        if (!"".equals(LastNameFilter.getText())){

            forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getLname().equals(LastNameFilter.getText());
                }
            }).collect(Collectors.toList());

        }
        // Entrance date filtering
        if (EdateFromFilter.getValue() != null){

            forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getEntrancedate().isAfter(EdateFromFilter.getValue());
                }
            }).collect(Collectors.toList());

        }

        if (EdToFilter.getValue() != null){

            forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getEntrancedate().isBefore(EdToFilter.getValue());
                }
            }).collect(Collectors.toList());

        }
        // Release date filtering
        if (RdateFromFilter.getValue() != null){

            forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getReleasedate().isAfter(RdateFromFilter.getValue());
                }
            }).collect(Collectors.toList());

        }

        if (RdateToFilter.getValue() != null){

            forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getReleasedate().isBefore(RdateToFilter.getValue());
                }
            }).collect(Collectors.toList());

        }
        //Sec lvl filtering

        if (SecLvlFilter.getValue() != null){

            forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getSecuritylvl().equals(SecLvlFilter.getValue());
                }
            }).collect(Collectors.toList());

        }
        //Cell num filtering

        if (!"".equals(CellNumberFilter.getText())){
             forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getCellnumber() == Integer.parseInt(CellNumberFilter.getText());
                }
            }).collect(Collectors.toList());



        }



        //Crime filtering
        if (CrimeFilter.getValue() != null){

            forprisonerfilter = forprisonerfilter.stream().filter(new Predicate<Prisoner>() {
                @Override
                public boolean test(Prisoner prisoner) {
                    return prisoner.getCrime().equals(CrimeFilter.getValue());
                }
            }).collect(Collectors.toList());

        }




        TitledPaneFIlter.setExpanded(false);
        clearListViewPrisoner();
        clearItemListPrisoner();
        for (Prisoner p: forprisonerfilter) {
            pids.add(p.getUniqueID());
            fnlist.add(p.getFname());
            lnlist.add(p.getLname());
            rdlist.add(p.getReleasedate().toString());
            edlist.add(p.getEntrancedate().toString());
            crimelist.add(p.getCrime());
            seclevellist.add(p.getSecuritylvl());
            celnum.add(p.getCellnumber());
        }
        updatePrisonerListView();

        forExceloutput = forprisonerfilter;

    }

    @FXML
    public void MakeExcelPrisonerFile(ActionEvent event) {

        File file = fc.showSaveDialog(new Stage());

        if (file != null){
            saveFile(file.getAbsolutePath());
        }

        Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);

        alertwindow.setTitle("Information");
        alertwindow.setContentText("The file was succesfully saved");
        alertwindow.showAndWait();

    }

    @FXML
    void ListReset(ActionEvent event) {
        TitledPaneFIlter.setExpanded(false);
        clearListViewPrisoner();
        clearItemListPrisoner();
        FillAllListofPrisoner();
        updatePrisonerListView();

    }
    
    @FXML
    public void ClearFilterSearch(ActionEvent event) {
         CellNumberFilter.setText("");
         CrimeFilter.setValue(null);
         EdToFilter.setValue(null);
         EdateFromFilter.setValue(null);
         FirstNameFilter.setText("");
         LastNameFilter.setText("");
         RdateFromFilter.setValue(null);
         RdateToFilter.setValue(null);
         SecLvlFilter.setValue(null);

    }

    @FXML
    public void WardenSearchFromFilter(ActionEvent event) {

        List<Warden> forwardenfilter = new ArrayList<>(wardens);

        if (!"".equals(FNWardenfilter.getText())){
            forwardenfilter = forwardenfilter.stream().filter(new Predicate<Warden>() {
                @Override
                public boolean test(Warden warden) {
                    return warden.getFname().equals(FNWardenfilter.getText());
                }
            }).collect(Collectors.toList());
        }

        if (!"".equals(LNWardenfilter.getText())){
            forwardenfilter = forwardenfilter.stream().filter(new Predicate<Warden>() {
                @Override
                public boolean test(Warden warden) {
                    return warden.getLname().equals(LNWardenfilter.getText());
                }
            }).collect(Collectors.toList());
        }

        if (JoinAfterDate.getValue() != null){
            forwardenfilter = forwardenfilter.stream().filter(new Predicate<Warden>() {
                @Override
                public boolean test(Warden warden) {
                    return warden.getJoinDate().isAfter(JoinAfterDate.getValue());
                }
            }).collect(Collectors.toList());
        }

        if (JoinBeforeDate.getValue() != null){
            forwardenfilter = forwardenfilter.stream().filter(new Predicate<Warden>() {
                @Override
                public boolean test(Warden warden) {
                    return warden.getJoinDate().isBefore(JoinBeforeDate.getValue());
                }
            }).collect(Collectors.toList());
        }

        if (RankFilter.getValue() != null){
            forwardenfilter = forwardenfilter.stream().filter(new Predicate<Warden>() {
                @Override
                public boolean test(Warden warden) {
                    return warden.getRank().equals(RankFilter.getValue());
                }
            }).collect(Collectors.toList());
        }

        if (FloorFilter.getValue() != null){
            forwardenfilter = forwardenfilter.stream().filter(new Predicate<Warden>() {
                @Override
                public boolean test(Warden warden) {
                    return warden.getFloorInCharge().equals(FloorFilter.getValue());
                }
            }).collect(Collectors.toList());
        }


        WardenTitledFilter.setExpanded(false);
        clearListViewWarden();
        clearItemListWarden();
        for (Warden w:forwardenfilter) {
            wardenids.add(w.getUnique_ID());
            wardenfloors.add(w.getFloorInCharge());
            wardenlns.add(w.getLname());
            wardenfns.add(w.getFname());
            wardenjds.add(w.getJoinDate().toString());
            wardenranks.add(w.getRank());
        }
        updateWardenListView();

        WardensforExcelOutput = forwardenfilter;

    }

    @FXML
    public void WardenResetList(ActionEvent event) {
        WardenTitledFilter.setExpanded(false);
        clearListViewWarden();
        clearItemListWarden();
        FillAllListofWarden();
        updateWardenListView();
    }

    @FXML
    public void WardenMakeExcelFile(ActionEvent event) {
        File file = fc.showSaveDialog(new Stage());

        if (file != null){
            saveWardenFile(file.getAbsolutePath());
        }

        Alert alertwindow = new Alert(Alert.AlertType.INFORMATION);

        alertwindow.setTitle("Information");
        alertwindow.setContentText("The file was succesfully saved");
        alertwindow.showAndWait();
    }

    @FXML
    public void ClearWardenFilters(ActionEvent event) {
          FNWardenfilter.setText("");
          LNWardenfilter.setText("");
          JoinAfterDate.setValue(null);
          JoinBeforeDate.setValue(null);
          RankFilter.setValue(null);
          FloorFilter.setValue(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        fc.setInitialDirectory(new File("C:\\"));

        selectedPrison.setText(LoginController.selectedPrison);

        if (LoginController.isAdmin){
            loginStatusLabel.setText("Admin");
        }else loginStatusLabel.setText("Guest");

        FillAllListofWarden();
        FillAllListofPrisoner();

        String[] rangok =   {"Guard","Small chief","Big chief"};
        List<String> crimes = new ArrayList<>(FileUtils.readCrimes("crimes.txt"));
        Warden_Rank.getItems().addAll(rangok);
        RankFilter.getItems().addAll(rangok);
        List<String> floors = new ArrayList<>();
        for (int i = 1; i <= getFloorByPrisonId(LoginController.selectedPrison) ; i++) {
            floors.add(Integer.toString(i));
        }
        Warden_Floor.getItems().addAll(floors);
        FloorFilter.getItems().addAll(floors);

        Crime.getItems().addAll(crimes);
        CrimeFilter.getItems().addAll(crimes);

        String[] secLevel = {"Low","Medium", "High"};
        SecLevel.getItems().addAll(secLevel);
        SecLvlFilter.getItems().addAll(secLevel);

        updatePrisonerListView();
        updateWardenListView();

        List<Login> logins = new ArrayList<>(loginDAO.getLogins());

        List<String> usernames = new ArrayList<>();
        List<String> logInTime = new ArrayList<>();
        List<String> logOutTime = new ArrayList<>();

        for (Login l:logins) {
            usernames.add(l.getUsername());
            logInTime.add(l.getLogin());
            logOutTime.add(l.getLogout());
        }

        Username_ListView.getItems().addAll(usernames);
        LogIn_ListView.getItems().addAll(logInTime);
        LogOut_ListView.getItems().addAll(logOutTime);


    }

    private void saveFile(String filename){
        if (forExceloutput.isEmpty()) forExceloutput = getPrisonersWhere();
        FileUtils.creatPrisonerExcelFile(forExceloutput,filename);
    }

    private void saveWardenFile(String filename){
        if (WardensforExcelOutput.isEmpty()) WardensforExcelOutput = getWardensWhere();
        FileUtils.creatWardenExcelFile(WardensforExcelOutput,filename);
    }

    private int getFloorByPrisonId(String prisonname){
        int floor=0;

        PrisonDAO prisonDAO = new JpaPrisonDAO();
        List<Prison> prisons = new ArrayList<>(prisonDAO.getPrisons());
        for (Prison p:prisons) {
            if (p.getPrisonName().equals(prisonname)) return p.getFloorNumber();
        }


        return floor;
    }

    public List<Prisoner> getPrisonersWhere() {
        PrisonerDAO prisonerDAO = new JpaPrisonerDAO();
        List<Prisoner> prisoners;
        prisoners = prisonerDAO.getPrisoners();

        List<Prisoner> out = new ArrayList<>();

        for (Prisoner p:prisoners) {
            if (p.getPrisonid() == LoginController.idOfSelectedPrison)
                out.add(p);
        }

        return out;
    }

    public List<Warden> getWardensWhere() {
        WardenDAO wardenDAO = new JpaWardenDAO();
        List<Warden> wardens;
        wardens = wardenDAO.getWardens();

        List<Warden> out = new ArrayList<>();

        for (Warden w:wardens) {
            if (w.getPrisonId() == LoginController.idOfSelectedPrison)
                out.add(w);
        }

        return out;
    }

    private void FillAllListofPrisoner(){
        List<Prisoner> prisoners = new ArrayList<>(getPrisonersWhere());
        clearItemListPrisoner();
        for (Prisoner p:prisoners) {
            pids.add(p.getUniqueID());
            fnlist.add(p.getFname());
            lnlist.add(p.getLname());
            rdlist.add(p.getReleasedate().toString());
            edlist.add(p.getEntrancedate().toString());
            crimelist.add(p.getCrime());
            seclevellist.add(p.getSecuritylvl());
            celnum.add(p.getCellnumber());
        }

    }

    private void FillAllListofWarden(){
        List<Warden> wardens = new ArrayList<>(getWardensWhere());
        clearItemListWarden();
        for (Warden w:wardens) {
            wardenids.add(w.getUnique_ID());
            wardenfloors.add(w.getFloorInCharge());
            wardenlns.add(w.getLname());
            wardenfns.add(w.getFname());
            wardenjds.add(w.getJoinDate().toString());
            wardenranks.add(w.getRank());
        }
    }

    private void handleWardeData(WardenDAO wDAO){
        Warden warden = new Warden();

        warden.setUnique_ID(Integer.parseInt(WardenID.getText()));
        warden.setFname(Warden_FN.getText());
        warden.setLname(Warden_LN.getText());
        warden.setJoinDate(Warden_JD.getValue());
        warden.setRank(Warden_Rank.getValue());
        warden.setFloorInCharge(Warden_Floor.getValue());
        warden.setPrisonId(LoginController.idOfSelectedPrison);
        wDAO.saveWarden(warden);

        WardenFN_list.getItems().add(Warden_FN.getText());
        WardenFloor_list.getItems().add(Warden_Floor.getValue());
        Wardenid_list.getItems().add(Integer.parseInt(WardenID.getText()));
        WardenJD_list.getItems().add(Warden_JD.getValue().toString());
        WardenLN_list.getItems().add(Warden_LN.getText());
        WardenRank_list.getItems().add(Warden_Rank.getValue());

    }

    private void handlePrisonerData(PrisonerDAO pDAO){
        Prisoner prisoner = new Prisoner();
        prisoner.setUniqueID(Integer.parseInt(PrisonerID.getText()));
        prisoner.setFname(Prisoner_FN.getText());
        prisoner.setLname(Prisoner_LN.getText());
        prisoner.setEntrancedate(EntranceDate.getValue());
        prisoner.setReleasedate(ReleaseDate.getValue());
        prisoner.setSecuritylvl(SecLevel.getValue());
        prisoner.setCellnumber(Integer.parseInt(Cell_Number.getText()));
        prisoner.setCrime(Crime.getValue());
        prisoner.setPrisonid(LoginController.idOfSelectedPrison);
        pDAO.savePrisoner(prisoner);

        PrisonerID_list.getItems().add(prisoner.getUniqueID());
        PrisonerRD_list.getItems().add(prisoner.getReleasedate().toString());
        PrisonerSL_list.getItems().add(prisoner.getSecuritylvl());
        PrisonerLN_list.getItems().add(prisoner.getLname());
        PrisonerCrime_list.getItems().add(prisoner.getCrime());
        PrisonerED_list.getItems().add(prisoner.getEntrancedate().toString());
        PrisonerFN_list.getItems().add(prisoner.getFname());
        PrisonerCN_list.getItems().add(prisoner.getCellnumber());
    }

    private void clearWardenInputs(){
        WardenID.clear();
        Warden_FN.clear();
        Warden_LN.clear();
        Warden_JD.setValue(null);
        Warden_Floor.setValue(null);
        Warden_Rank.setValue(null);
    }

    private void clearPrisonerInput(){
        PrisonerID.clear();
        Prisoner_FN.clear();
        Prisoner_LN.clear();
        EntranceDate.setValue(null);
        ReleaseDate.setValue(null);
        SecLevel.setValue(null);
        Crime.setValue(null);
        Cell_Number.clear();
    }

    private void clearListViewPrisoner(){

        PrisonerID_list.getItems().clear();
        PrisonerRD_list.getItems().clear();
        PrisonerSL_list.getItems().clear();
        PrisonerLN_list.getItems().clear();
        PrisonerCrime_list.getItems().clear();
        PrisonerED_list.getItems().clear();
        PrisonerFN_list.getItems().clear();
        PrisonerCN_list.getItems().clear();


    }

    private void clearItemListPrisoner(){
        pids.clear();
        fnlist.clear();
        lnlist.clear();
        rdlist.clear();
        edlist.clear();
        crimelist.clear();
        seclevellist.clear();
        celnum.clear();
    }

    private void updatePrisonerListView(){
        PrisonerID_list.getItems().addAll(pids);
        PrisonerRD_list.getItems().addAll(rdlist);
        PrisonerSL_list.getItems().addAll(seclevellist);
        PrisonerLN_list.getItems().addAll(lnlist);
        PrisonerCrime_list.getItems().addAll(crimelist);
        PrisonerED_list.getItems().addAll(edlist);
        PrisonerFN_list.getItems().addAll(fnlist);
        PrisonerCN_list.getItems().addAll(celnum);
    }

    private void clearListViewWarden(){

        WardenFN_list.getItems().clear();
        WardenFloor_list.getItems().clear();
        Wardenid_list.getItems().clear();
        WardenJD_list.getItems().clear();
        WardenLN_list.getItems().clear();
        WardenRank_list.getItems().clear();


    }

    private void clearItemListWarden(){
        wardenids.clear();
        wardenfloors.clear();
        wardenlns.clear();
        wardenfns.clear();
        wardenjds.clear();
        wardenranks.clear();
    }

    private void updateWardenListView(){
        WardenFN_list.getItems().addAll(wardenfns);
        WardenFloor_list.getItems().addAll(wardenfloors);
        Wardenid_list.getItems().addAll(wardenids);
        WardenJD_list.getItems().addAll(wardenjds);
        WardenLN_list.getItems().addAll(wardenlns);
        WardenRank_list.getItems().addAll(wardenranks);
    }

    private boolean isString(String str){
        String abc = "qwertzuiopőúóüöűáélkjhgfdsaíyxcvbnm";
        for (int i = 0; i < str.length(); i++) {
            if (abc.contains(Character.toString(str.charAt(i)))){
                return true;
            }
        }
        return false;
    }

    private boolean containsWardenID(List<Warden> wardens,int id){
        return wardens.stream().anyMatch(o -> o.getUnique_ID()== id);
    }
}