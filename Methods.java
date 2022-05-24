import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;
public class Methods {
    public boolean isValidPasswordOrNot(String password) {
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
        Pattern p = Pattern.compile(regex);
        if (password == null)
            return false;
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public void displayAllSongs(String childOrAdult) {
        try {
            int sid, selectedId;
            String sName, aName, gen;
            Scanner sc = new Scanner(System.in);
            List<String> link = new ArrayList<>();
            List<String> desc = new ArrayList<>();
            List<String> songName = new ArrayList<>();
            List<String> albumName = new ArrayList<>();
            List<String> genre = new ArrayList<>();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            if (childOrAdult.equalsIgnoreCase("adultsongs")) {
                ps = con.prepareStatement("select  distinct sid,sname,a.alname,duration,g.gname,link,descriptionofsong from adult_song s join adult_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            } else if (childOrAdult.equalsIgnoreCase("childsongs")) {
                ps = con.prepareStatement("select  distinct sid,sname,a.alname,duration,g.gname,link,descriptionofsong from child_song s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();

            } else {
                ps = con.prepareStatement("select  distinct sid,sname,a.alname,duration,g.gname,link,descriptionofsong from child_rhymes s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            }
            System.out.format("%30s %40s %30s %30s ", "SongId", "Song Name", "Album", "Duration");
            System.out.println();
            System.out.println("            _________________________________________________________________________________________________________________________________________");
            while (rs.next()) {
                sid = rs.getInt(1);
                sName = rs.getString(2);
                aName = rs.getString(3);
                gen = rs.getString(5);
                System.out.format("%30d %40s %30s %30s", sid, sName, aName, rs.getTime(4));
                System.out.println();
                link.add(rs.getString(6));
                desc.add(rs.getString(7));
                songName.add(sName);
                albumName.add(aName);
                genre.add(gen);

            }
            System.out.print("ENTER SONG ID:");
            selectedId = sc.nextInt();
            if (MainClass.login == true) {
                System.out.println("SONG NAME :" + songName.get(selectedId - 1) + "\nALBUM NAME :" + albumName.get(selectedId - 1));
                System.out.println("GENRE :" + genre.get(selectedId - 1));
                System.out.println("DESCRIPTION :" + desc.get(selectedId - 1));

                AudioPlayer.playMusic(link.get(selectedId - 1));
            } else {
                System.out.println("\nFOR LISTENING SONGS OR PODCAST YOU NEED TO RESTART APPLICATION AND CREATE OR LOGIN IN TO THE ACCOUNT\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBasedOnGenre(String childOrAdult) {
        try {
            int sid = 1, selectedId;
            String sName, aName, gen;
            Scanner sc = new Scanner(System.in);
            List<String> link = new ArrayList<>();
            List<String> desc = new ArrayList<>();
            List<String> songName = new ArrayList<>();
            List<String> albumName = new ArrayList<>();
            List<String> genre = new ArrayList<>();
            int count = 1, selectedGenreId;
            String genretype;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            if (childOrAdult.equalsIgnoreCase("childsongs")) {
                ps = con.prepareStatement("select  distinct g.gname from child_song s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            } else if (childOrAdult.equalsIgnoreCase("adultsongs")) {
                ps = con.prepareStatement("select  distinct g.gname from adult_song s join adult_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement("select  distinct g.gname from child_rhymes s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            }
            System.out.format("%30s %30s", "GenreId", "Genre Name");
            System.out.println();
            System.out.println("            _____________________________________________________________________");
            while (rs.next()) {
                System.out.format("%30d %30s", count, rs.getString(1));
                System.out.println();
                genre.add(rs.getString(1));
                count++;
            }
            System.out.println("\nENTER GENRE ID");
            selectedGenreId = sc.nextInt();
            genretype = genre.get(selectedGenreId - 1);
            if (childOrAdult.equalsIgnoreCase("adultsongs")) {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from adult_song s join adult_album a on a.alid=s.alid join genre g on g.gid=s.gid where gname=?");
            } else if (childOrAdult.equalsIgnoreCase("childsongs")) {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from child_song s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid where gname=?");
            } else {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from child_rhymes s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid where gname=?");
            }
            ps.setString(1, genretype);
            rs = ps.executeQuery();
            System.out.println("\n");
            System.out.format("%30s %40s %30s %30s ", "SongId", "Song Name", "Album", "Duration");
            System.out.println();
            System.out.println("            _________________________________________________________________________________________________________________________________________");
            while (rs.next()) {
                sName = rs.getString(1);
                aName = rs.getString(2);
                gen = rs.getString(4);
                System.out.format("%30d %40s %30s %30s", sid, sName, aName, rs.getTime(3));
                System.out.println();
                link.add(rs.getString(5));
                desc.add(rs.getString(6));
                songName.add(sName);
                albumName.add(aName);
                genre.add(gen);
                sid++;
            }
            System.out.print("ENTER SONG ID:");
            selectedId = sc.nextInt();
            if (MainClass.login == true) {
                System.out.println("SONG NAME :" + songName.get(selectedId - 1) + "\nALBUM NAME :" + albumName.get(selectedId - 1));
                System.out.println("GENRE :" + genre.get(selectedId - 1));
                System.out.println("DESCRIPTION :" + desc.get(selectedId - 1));

                AudioPlayer.playMusic(link.get(selectedId - 1));
            } else {
                System.out.println("\nFOR LISTENING SONGS OR PODCAST YOU NEED TO RESTART APPLICATION AND CREATE OR LOGIN IN TO THE ACCOUNT\n");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBasedOnArtist(String childOrAdult) {
        try {
            int sid = 1, selectedId;
            String sName, aName, gen;
            Scanner sc = new Scanner(System.in);
            List<String> link = new ArrayList<>();
            List<String> desc = new ArrayList<>();
            List<String> songName = new ArrayList<>();
            List<String> albumName = new ArrayList<>();
            List<String> genre = new ArrayList<>();
            int count = 1, selectedArtistId;
            String artistName;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            if (childOrAdult.equalsIgnoreCase("childsongs")) {
                ps = con.prepareStatement("select  distinct arname from child_song s join artists a on a.arid=s.arid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            } else if (childOrAdult.equalsIgnoreCase("adultsongs")) {
                ps = con.prepareStatement("select  distinct arname from adult_song s join artists a on a.arid=s.arid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement("select  distinct arname from child_rhymes s join artists a on a.arid=s.arid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            }
            System.out.format("%30s %30s", "ArtistId", "Artist Name");
            System.out.println();
            System.out.println("            _____________________________________________________________________");
            while (rs.next()) {
                System.out.format("%30d %30s", count, rs.getString(1));
                System.out.println();
                genre.add(rs.getString(1));
                count++;
            }
            System.out.println("\nENTER ARTIST ID");
            selectedArtistId = sc.nextInt();
            artistName = genre.get(selectedArtistId - 1);
            if (childOrAdult.equalsIgnoreCase("adultsongs")) {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from adult_song s join adult_album a on a.alid=s.alid join genre g on g.gid=s.gid join artists ar on s.arid=ar.arid where arname=?");
            } else if (childOrAdult.equalsIgnoreCase("childsongs")) {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from child_song s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid join artists ar on s.arid=ar.arid where arname=?");
            } else {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from child_rhymes s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid join artists ar on s.arid=ar.arid where arname=?");
            }
            ps.setString(1, artistName);
            rs = ps.executeQuery();
            System.out.println("\n");
            System.out.format("%30s %40s %30s %30s ", "SongId", "Song Name", "Album", "Duration");
            System.out.println();
            System.out.println("            _________________________________________________________________________________________________________________________________________");
            while (rs.next()) {
                sName = rs.getString(1);
                aName = rs.getString(2);
                gen = rs.getString(4);
                System.out.format("%30d %40s %30s %30s", sid, sName, aName, rs.getTime(3));
                System.out.println();
                link.add(rs.getString(5));
                desc.add(rs.getString(6));
                songName.add(sName);
                albumName.add(aName);
                genre.add(gen);
                sid++;
            }
            System.out.print("ENTER SONG ID :");
            selectedId = sc.nextInt();
            if (MainClass.login == true) {
                System.out.println("SONG NAME :" + songName.get(selectedId - 1) + "\nALBUM NAME :" + albumName.get(selectedId - 1));
                System.out.println("GENRE :" + genre.get(selectedId - 1));
                System.out.println("DESCRIPTION :" + desc.get(selectedId - 1));

                AudioPlayer.playMusic(link.get(selectedId - 1));
            } else {
                System.out.println("\nFOR LISTENING SONGS OR PODCAST YOU NEED TO RESTART APPLICATION AND CREATE OR LOGIN IN TO THE ACCOUNT\n");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayBasedOnAlbum(String childOrAdult) {
        try {
            Scanner sc = new Scanner(System.in);
            int count = 1, selectedAlbumId;
            String alName;
            int sid = 1, selectedId;
            String sName, aName, gen;
            List<String> link = new ArrayList<>();
            List<String> desc = new ArrayList<>();
            List<String> songName = new ArrayList<>();
            List<String> albumName = new ArrayList<>();
            List<String> genre = new ArrayList<>();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            if (childOrAdult.equalsIgnoreCase("childsongs")) {
                ps = con.prepareStatement("select  distinct alname from child_song s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            } else if (childOrAdult.equalsIgnoreCase("adultsongs")) {
                ps = con.prepareStatement("select  distinct alname from adult_song s join adult_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement("select  distinct alname from child_rhymes s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid order by sid");
                rs = ps.executeQuery();
            }
            System.out.format("%30s %30s", "ArtistId", "Artist Name");
            System.out.println();
            System.out.println("            _____________________________________________________________________");
            while (rs.next()) {
                System.out.format("%30d %30s", count, rs.getString(1));
                System.out.println();
                albumName.add(rs.getString(1));
                count++;
            }
            System.out.println("\nENTER ALBUM ID");
            selectedAlbumId = sc.nextInt();
            alName = albumName.get(selectedAlbumId - 1);
            if (childOrAdult.equalsIgnoreCase("adultsongs")) {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from adult_song s join adult_album a on a.alid=s.alid join genre g on g.gid=s.gid join artists ar on s.arid=ar.arid where alname=?");
            } else if (childOrAdult.equalsIgnoreCase("childsongs")) {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from child_song s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid join artists ar on s.arid=ar.arid where alname=?");
            } else {
                ps = con.prepareStatement("select  distinct sname,a.alname,duration,g.gname,link,descriptionofsong from child_rhymes s join child_album a on a.alid=s.alid join genre g on g.gid=s.gid join artists ar on s.arid=ar.arid where alname=?");
            }
            ps.setString(1, alName);
            rs = ps.executeQuery();
            System.out.println("\n");
            System.out.format("%30s %40s %30s %30s ", "SongId", "Song Name", "Album", "Duration");
            System.out.println();
            System.out.println("            _________________________________________________________________________________________________________________________________________");
            while (rs.next()) {
                sName = rs.getString(1);
                aName = rs.getString(2);
                gen = rs.getString(4);
                System.out.format("%30d %40s %30s %30s", sid, sName, aName, rs.getTime(3));
                System.out.println();
                link.add(rs.getString(5));
                desc.add(rs.getString(6));
                songName.add(sName);
                albumName.add(aName);
                genre.add(gen);
                sid++;
            }
            System.out.print("Enter Song Id:");
            selectedId = sc.nextInt();
            if (MainClass.login == true) {
                System.out.println("SONG NAME :" + songName.get(selectedId - 1) + "\nALBUM NAME :" + albumName.get(selectedId - 1));
                System.out.println("GENRE :" + genre.get(selectedId - 1));
                System.out.println("DESCRIPTION :" + desc.get(selectedId - 1));

                AudioPlayer.playMusic(link.get(selectedId - 1));
            } else {
                System.out.println("\nFOR LISTENING SONGS OR PODCAST YOU NEED TO RESTART APPLICATION AND CREATE OR LOGIN IN TO THE ACCOUNT\n");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void displayAllPodcasts(String childOrAdult) {
        try {
            List<Integer> podId = new ArrayList<>();
            List<Integer> epiId = new ArrayList<>();
            List<String> epiNames = new ArrayList<>();
            List<String> podNames = new ArrayList<>();
            List<String> descofepis = new ArrayList<>();
            List<String> links = new ArrayList<>();
            List<String> genres = new ArrayList<>();
            List<String> onAirDates = new ArrayList<>();
            int epid, selectedEpisodeId, dayDiff;
            String episodename, onAirDate = "", descriptionofepisode, gname, link = "";
            Scanner sc = new Scanner(System.in);
            int podcastId, selectedPodcastId, podid;
            String podcastName;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            if (childOrAdult.equalsIgnoreCase("childpodcast")) {
                ps = con.prepareStatement("select podid,podname from child_podcast");
            } else {
                ps = con.prepareStatement("select podid,podname from adult_podcast");
            }
            rs = ps.executeQuery();
            System.out.format("%30s %30s", "Podcast Id", "Podcast Name");
            System.out.println();
            System.out.println("            _____________________________________________________________________");
            while (rs.next()) {
                podcastId = rs.getInt(1);
                podcastName = rs.getString(2);
                System.out.format("%30d %30s", podcastId, rs.getString(2));
                System.out.println();
                podId.add(podcastId);
            }
            System.out.println("ENTER PODCAST ID :");
            selectedPodcastId = sc.nextInt();
            podid = podId.get(selectedPodcastId - 1);
            ps = con.prepareStatement("select podname from adult_podcast where podid=?");
            ps.setInt(1, selectedPodcastId);
            rs = ps.executeQuery();
            rs.next();
            podcastName = rs.getString(1);
            if (childOrAdult.equalsIgnoreCase("childpodcast")) {
                ps = con.prepareStatement("select distinct epid,episodename,onAirDate,descriptionofepisode,gname,link,epitime from child_podcast_episodes cpe join genre on genre.gid=cpe.gid where cpe.podid=?");
                ps.setInt(1, podid);
            } else {
                ps = con.prepareStatement("select distinct epid,episodename,onAirDate,descriptionofepisode,gname,link,epitime from adult_podcast_episodes cpe join genre on genre.gid=cpe.gid where cpe.podid=?");
                ps.setInt(1, podid);
            }
            rs = ps.executeQuery();
            System.out.println("\n");
            System.out.format("%30s %40s %30s %30s ", "Episode Id", "Episode Name", "Genre", "Duration");
            System.out.println();
            System.out.println("            _________________________________________________________________________________________________________________________________________");
            while (rs.next()) {
                epid = rs.getInt(1);
                episodename = rs.getString(2);
                onAirDate = rs.getString(3);
                descriptionofepisode = rs.getString(4);
                gname = rs.getString(5);
                link = rs.getString(6);
                System.out.format("%30d %40s %30s %30s", epid, episodename, gname, rs.getString(7));
                System.out.println();
                epiId.add(epid);
                epiNames.add(episodename);
                onAirDates.add(onAirDate);
                descofepis.add(descriptionofepisode);
                genres.add(gname);
                links.add(link);
            }
            System.out.print("\nENTER EPISODE ID :");
            selectedEpisodeId = sc.nextInt();
            if (MainClass.login == true) {
                ps = con.prepareStatement("SELECT DATEDIFF(?,now()) AS DateDiff");
                ps.setString(1, onAirDate);
                rs = ps.executeQuery();
                rs.next();
                dayDiff = rs.getInt(1);
                if (dayDiff <= 0) {
                    System.out.println("EPISODE NAME :" + epiNames.get(selectedEpisodeId - 1) + "\nPODCAST NAME :" + podcastName);
                    System.out.println("GENRE :" + genres.get(selectedEpisodeId - 1));
                    System.out.println("DESCRIPTION :" + descofepis.get(selectedEpisodeId - 1));

                    AudioPlayer.playMusic(links.get(selectedEpisodeId - 1));
                } else {
                    System.out.println("THIS EPISODE IS NOT YET RELEASED.WILL BE RELEASED IN " + dayDiff + " DAYS. RELEASE DATE - " + onAirDates.get(selectedEpisodeId - 1));
                }
            } else {
                System.out.println("\nFOR LISTENING SONGS OR PODCAST YOU NEED TO RESTART APPLICATION AND CREATE OR LOGIN IN TO THE ACCOUNT\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void upcomingLiveStreams(String childOrAdult) {
        try {
            String date, episodeName, podname, genreName, link, onAirDate;
            int epid, count = 1;
            String epitime;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs, rs1, rs2;
            if (childOrAdult.equalsIgnoreCase("childpodcast")) {
                ps = con.prepareStatement("select distinct onAirDate from  child_podcast_episodes");
                rs = ps.executeQuery();
            } else {
                ps = con.prepareStatement("select distinct onAirDate from  adult_podcast_episodes");
                rs = ps.executeQuery();
            }
            while (rs.next()) {
                date = rs.getString(1);
                ps = con.prepareStatement("SELECT DATEDIFF(?,now()) AS DateDiff");
                ps.setString(1, date);
                rs1 = ps.executeQuery();
                rs1.next();
                if (rs1.getInt(1) > 0) {
                    if (count == 1) {
                        System.out.format("%30s %40s %30s %20s %30s %30s", "EPISODE ID", "EPISODE NAME", "PODCAST NAME", "GENRE NAME", "EPISODE TIME", "ON AIR DATE");
                        System.out.println();
                        System.out.format("                _________________________________________________________________________________________________________________________________________________________________________");
                        System.out.println();
                        count++;
                    }
                    if (childOrAdult.equalsIgnoreCase("childpodcast"))
                        ps = con.prepareStatement("select episodeName,epid,podname,g.gname,epitime,link,onAirDate from child_podcast cp join child_podcast_episodes cpe on cp.podid=cpe.podid join genre g on g.gid=cpe.gid where onAirDate=?");
                    else
                        ps = con.prepareStatement("select episodeName,epid,podname,g.gname,epitime,link,onAirDate from adult_podcast cp join adult_podcast_episodes cpe on cp.podid=cpe.podid join genre g on g.gid=cpe.gid where onAirDate=?");
                    ps.setString(1, date);
                    rs2 = ps.executeQuery();
                    rs2.next();
                    epid = rs2.getInt(2);
                    episodeName = rs2.getString(1);
                    podname = rs2.getString(3);
                    genreName = rs2.getString(4);
                    epitime = rs2.getTime(5).toString();
                    link = rs2.getString(6);
                    onAirDate = rs2.getString(7);
                    System.out.format("%30d %40s %30s %20s %30s %30s", epid, episodeName, podname, genreName, epitime, onAirDate);
                    System.out.println();
                }
            }
            if (count == 1) {
                System.out.println("NO LIVE SESSIONS ARE AVAILABLE.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


