import java.sql.*;
import java.util.Scanner;

public class MainClass {
     static boolean login = false;
    public static void main(String[] args) {
        Methods me= new Methods();
        PlayList p=new PlayList();
        String name,city,username,password="";
        int age=0,userId=0,loginId=0;
        long phoneNumber;
        boolean boolpassword;
        int choiceForSongsOrPodcast = 0, exit = 0, choice = 0, choiceForChildrenOrAdultSongs = 0, songsOrPodcasts = 0, childChoiceForDisplayingorCreatingPlayListOfSongs = 0, childrenSongsOrRhymes = 0, choiceForChildrenOrAdultPodcasts = 0, ChoiceForDisplayingorCreatingPlayListPodcast = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("***********************************************************************-JUKE BOX - A PLACE WHERE YOU CAN REFRESH YOUR MOOD-**********************************************************");
        while(true){
            System.out.println("\nENTER CHOICE : \n1 - SIGN UP \n2 - LOGIN. \n3 - SKIP");
            choice = sc.nextInt();
            if(choice==1 || choice==2 || choice==3)
                break;
            else
                System.out.println("INVALID OPTION");
        }
        sc.nextLine();
        if (choice == 1) {
            System.out.println("ENTER NAME,PHONE NUMBER,AGE,CITY");
            name=sc.nextLine();
            phoneNumber=sc.nextLong();
            age= sc.nextInt();
            city=sc.next();
            try{
                Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","password@123");
                PreparedStatement ps=con.prepareStatement("insert into users(uname,phno,age,city) values(?,?,?,?)");
                ps.setString(1,name);
                ps.setLong(2,phoneNumber);
                ps.setInt(3,age);
                ps.setString(4,city);
                if(ps.executeUpdate()<0)
                    System.out.println("ERROR");
                System.out.println("ENTER USERNAME");
                username=sc.next();
                do{
                    System.out.println("ENTER PASSWORD\nIT SHOULD BE BETWEEN 8-20 CHARECTERS.\nIT SHOULD HAVE:\nONE UPPERCASE,\nONE LOWER CASE\nONE NUMBER\nONE SPECIAL CHARECTER(@#$%^&+=)");
                    password=sc.next();
                    boolpassword=me.isValidPasswordOrNot(password);
                    if(!boolpassword)
                        System.out.println("INVALID PASSWORD");
                }while(!boolpassword);
                ps=con.prepareStatement("select uid from users where uname=? and phno=? ");
                ps.setString(1,name);
                ps.setLong(2,phoneNumber);
                ResultSet rs= ps.executeQuery();
                if(rs.next()==false)
                    System.out.println("ERROR DURING CREATION OF ACCOUNT");
                else{
                    userId=rs.getInt(1);
                    ps=con.prepareStatement("insert into login(uid,username,upassword) values(?,?,?)");
                    ps.setInt(1,userId);
                    ps.setString(2,username);
                    ps.setString(3,password);
                    ps.executeUpdate();
                    ps=con.prepareStatement("select lid from login where username=?");
                    ps.setString(1,username);
                    rs=ps.executeQuery();
                    if(rs.next()==false)
                        System.out.println("ERROR");
                    else{
                        loginId=rs.getInt(1);
                        login=true;
                        System.out.println("WELCOME "+username.toUpperCase());
                    }
                }
            }catch(SQLException e){e.printStackTrace();}
        }
        else if (choice == 2){
           System.out.println("ENTER USERNAME");
           username= sc.nextLine();
           System.out.println("ENTER PASSWORD");
           password= sc.nextLine();
           try{
               Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox","root","password@123");
               PreparedStatement ps=con.prepareStatement("select users.uname,users.uid,login.lid from users join login on users.uid=login.uid where login.username=? and upassword=?");
               ps.setString(1,username);
               ps.setString(2,password);
               ResultSet rs=ps.executeQuery();
               if(rs.next()==false)
                   System.out.println("ACCOUNT NOT FOUND");
               else{
                   name=rs.getString(1);
                   userId=rs.getInt(2);
                   loginId=rs.getInt(3);
                   login=true;
                   System.out.println("WELCOME "+username.toUpperCase());
               }
           }catch(SQLException e){e.printStackTrace();}
        }
        else
        {
            System.out.println("WELCOME");
        }
        System.out.println("******************************************************************************************************************************************");
        while (true) {
            while(true){
            System.out.println("\nENTER YOUR CHOICE  1.SONGS   2.PODCASTS");
            choiceForSongsOrPodcast = sc.nextInt();
            if(choiceForSongsOrPodcast==1 || choiceForSongsOrPodcast==2)
                    break;
            else
                System.out.println("INVALID INPUT");}
            //for songs
            if (choiceForSongsOrPodcast == 1) {
                    while(true){
                        while (true){
                        System.out.println("\nCHOOSE SUITABLE OPTION : 1 - CHILDREN SONGS  2 - ADULT SONGS");
                        choiceForChildrenOrAdultSongs = sc.nextInt();
                        if(choiceForChildrenOrAdultSongs==1 || choiceForChildrenOrAdultSongs==2)
                            break;
                        else
                            System.out.println("INVALID INPUT");}
                        //for children songs
                        if (choiceForChildrenOrAdultSongs == 1) {
                            while (true){
                                System.out.println("\nENTER OPTION :    1 - CHILDREN SONGS    2 - CHILDREN RHYMES");
                                childrenSongsOrRhymes = sc.nextInt();
                                if (childrenSongsOrRhymes == 1 || childrenSongsOrRhymes == 2)
                                    break;
                                else
                                    System.out.println("ENTER A VALID INPUT");
                            }
                            if (childrenSongsOrRhymes == 1) {
                                System.out.println("\nENTER THE CHOICE.\n1 - DISPLAY ALL SONGS.\n2 - DISPLAY BASED ON ARTIST .\n3 - DISPLAY BASED ON ALBUM \n4 - DISPLAY BASED ON GENRE.\n5 - CREATING PLAYLIST" );
                                childChoiceForDisplayingorCreatingPlayListOfSongs = sc.nextInt();
                                switch (childChoiceForDisplayingorCreatingPlayListOfSongs) {
                                    case 1:
                                        me.displayAllSongs("childsongs");
                                        break;
                                    case 2:
                                        me.displayBasedOnArtist("childsongs");
                                        break;
                                    case 3:
                                        me.displayBasedOnAlbum("childsongs");
                                        break;
                                    case 4:
                                        me.displayBasedOnGenre("childsongs");
                                        break;
                                    case 5:
                                        p.playlist(loginId);
                                        break;
                                    default:
                                        System.out.println("INVALID CHOICE");
                                }
                            }
                            else{
                                System.out.println("ENTER THE CHOICE.\n1 - DISPLAY ALL SONGS.\n2 - DISPLAY BASED ON ARTIST .\n3 - DISPLAY BASED ON ALBUM \n4 - DISPLAY BASED ON GENRE.\n5 - PLAYLIST");
                                childChoiceForDisplayingorCreatingPlayListOfSongs = sc.nextInt();
                                switch (childChoiceForDisplayingorCreatingPlayListOfSongs) {
                                    case 1:
                                        me.displayAllSongs("childrhymes");
                                        break;
                                    case 2:
                                        me.displayBasedOnArtist("childrhymes");
                                        break;
                                    case 3:
                                        me.displayBasedOnAlbum("childrhymes");
                                        break;
                                    case 4:
                                        me.displayBasedOnGenre("childrhymes");
                                        break;
                                    case 5:
                                        p.playlist(loginId);
                                        break;
                                    default:
                                        System.out.println("INVALID CHOICE");
                                }
                            }
                        }
                        //for adult songs
                        else  {
                            System.out.println("ENTER THE CHOICE.\n1 - DISPLAY ALL SONGS.\n2 - DISPLAY BASED ON ARTIST .\n3 - DISPLAY BASED ON ALBUM \n4 - DISPLAY BASED ON GENRE.\n5 - PLAYLIST");
                            childChoiceForDisplayingorCreatingPlayListOfSongs = sc.nextInt();
                            switch (childChoiceForDisplayingorCreatingPlayListOfSongs) {
                                case 1:
                                    me.displayAllSongs("adultsongs");
                                    break;
                                case 2:
                                    me.displayBasedOnArtist("adultsongs");
                                    break;
                                case 3:
                                    me.displayBasedOnAlbum("adultsongs");
                                    break;
                                case 4:
                                    me.displayBasedOnGenre("adultsongs");
                                    break;
                                case 5:
                                    p.playlist(loginId);
                                    break;
                                default:
                                    System.out.println("INVALID CHOICE");
                            }
                        }
                        while(true){
                            System.out.println("TO GO BACK TO SONGS PRESS  '1'      FOR GOING BACK TO MAIN PAGE PRESS '0' ");
                            songsOrPodcasts=sc.nextInt();
                            if(songsOrPodcasts==1 ||songsOrPodcasts==0)
                                break;
                            else
                                System.out.println("INVALID OPTION");

                        }
                        if(songsOrPodcasts==0)
                            break;
                    }
            }
            //for podcasts
            else {
                        while(true){
                            while(true){
                            System.out.println("\nCHOOSE SUITABLE OPTION : 1 - CHILDREN PODCAST  2 - ADULT PODCAST");
                            choiceForChildrenOrAdultPodcasts = sc.nextInt();
                            if(choiceForChildrenOrAdultPodcasts==1 || choiceForChildrenOrAdultPodcasts==2)
                                    break;
                            else
                                System.out.println("INVALID INPUT");}
                            //for children podcast
                            if (choiceForChildrenOrAdultPodcasts== 1 || choiceForChildrenOrAdultPodcasts==2) {
                                System.out.println("ENTER THE CHOICE \n1 - DISPLAY ALL PODCASTS.\n2 - KNOW UPCOMING LIVE STREAMS.\n3 - PLAYLIST.");
                                ChoiceForDisplayingorCreatingPlayListPodcast = sc.nextInt();
                                switch (ChoiceForDisplayingorCreatingPlayListPodcast) {
                                    case 1:
                                        if(choiceForChildrenOrAdultPodcasts== 1 )
                                        {
                                                me.displayAllPodcasts("childpodcast");
                                                 break;
                                        }
                                        else
                                        {
                                            me.displayAllPodcasts("adultpodcast");
                                            break;
                                        }
                                    case 3:
                                        p.playlist(loginId);
                                           break;
                                    case 2:
                                        if(choiceForChildrenOrAdultPodcasts== 1 )
                                        {
                                            me.upcomingLiveStreams("childpodcast");
                                            break;
                                        }
                                        else
                                        {
                                            me.upcomingLiveStreams("adultpodcast");
                                            break;
                                        }
                                    default:
                                        System.out.println("INVALID CHOICE");
                                }
                            }
                            else
                                System.out.println("INVALID OPTION");
                            while(true){
                                System.out.println("TO GO BACK TO PODCAST PRESS  '1'      FOR GOING BACK TO MAIN PAGE PRESS '0' ");
                                songsOrPodcasts=sc.nextInt();
                                if(songsOrPodcasts==1 ||songsOrPodcasts==0)
                                    break;
                                else
                                    System.out.println("INVALID OPTION");

                            }
                            if(songsOrPodcasts==0)
                                break;
                        }

            }
            while(true){
                System.out.println("\nTO GO BACK TO SONGS OR PODCAST PRESS '1'    TO EXIT FROM APPLICATION PRESS '0' ");
                exit = sc.nextInt();
                if(exit==1 || exit==0)
                    break;
                else
                    System.out.println("INVALID INPUT");
            }
            if (exit == 0)
                break;

        }
        System.out.println("----------------------------------------------------------Have A nice day--------------------------------------------------------------");
    }
}
