import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayList {


    public void playlist(int loginId) {
        if (MainClass.login == true) {
        int choice = 0, breaking = 0, count = 1;
        Scanner sc = new Scanner(System.in);
        while (count == 1) {
            System.out.println("CHOOSE APPROPRIATE OPTION: 1 - CREATING NEW PLAYLIST      2 - DISPLAY PLAYLIST    3- DELETE PLAYLIST");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    this.creatingPlayList(loginId);
                    break;
                case 2:
                    this.displayPlayList(loginId);
                    break;
                case 3:
                    this.deletePlayList(loginId);
                    break;
                default:
                    System.out.println("INVALID OPTION");

            }
            while (true) {
                System.out.println("ENTER '1' TO CONTINUE IN PLAYLIST.  2 TO EXIT FROM PLAYLIST  ");
                breaking = sc.nextInt();
                if (breaking == 1 || breaking == 2) {
                    if (breaking == 2) {
                        count++;
                        break;
                    } else
                        break;
                } else
                    System.out.println("INVALID OPTION");

            }
        }
        }
        else
            System.out.println("FOR CREATING SONGS OR PODCAST YOU NEED TO RESTART APPLICATION AND CREATE OR LOGIN IN TO THE ACCOUNT");
    }


    public void creatingPlayList(int loginId) {
        Scanner sc = new Scanner(System.in);
        int addSongOrPodcast = 0, addOrPlay = 0, playSongOrPodcast, performOperations;
        System.out.println("ENTER THE NAME OF PLAYLIST");
        String playListName = sc.nextLine();
        int playlistId = 0;
        try {
            ResultSet rs;
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps = con.prepareStatement("insert into playlist (pname,createdon,lid) values (?,now(),?)");
            ps.setString(1, playListName);
            ps.setInt(2, loginId);
            int k = ps.executeUpdate();
            if (k > 0)
                System.out.println("PLAYLIST SUCCESSFULLY CREATED");
            ps = con.prepareStatement("select plid from playlist where pname=?");
            ps.setString(1, playListName);
            rs = ps.executeQuery();
            rs.next();
            playlistId = rs.getInt(1);
            while (true) {
                System.out.println("ENTER 1- FOR PERFORMING OPERATIONS ON THIS PLAYLIST 2- EXIT");
                performOperations = sc.nextInt();
                if (performOperations == 1) {
                    operations(playlistId, loginId);
                    break;
                } else if (performOperations == 2) {
                    break;
                } else
                    System.out.println("INVALID OPTION");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePlayList(int loginId) {
        Scanner sc = new Scanner(System.in);
        int count = 1, plid, selectedplayListId,c;
        String playListName, createdOn;
        List<Integer> plids=new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps = con.prepareStatement("select * from playlist where lid=?");
            ps.setInt(1, loginId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (count == 1) {
                    System.out.format("%30s %30s %30s", "PlayList Id", "PlayList Name", "Created On");
                    System.out.println();
                    System.out.println("           ____________________________________________________________");
                    count++;
                }
                plid = rs.getInt(1);
                playListName = rs.getString(2);
                createdOn = rs.getString(3);
                System.out.format("%30d %30s %30s", plid, playListName, createdOn);
                System.out.println();
                plids.add((plid));
            }

            System.out.println("ENTER PLAYLISTID TO DELETE");
            selectedplayListId = sc.nextInt();
            if(plids.contains(selectedplayListId))
            {   ps = con.prepareStatement("delete from child_rhymes_playlist where plid=?");
                ps.setInt(1, selectedplayListId);
                ps.executeUpdate();
                ps = con.prepareStatement("delete from child_songs_playlist where plid=?");
                ps.setInt(1, selectedplayListId);
                ps.executeUpdate();
                ps = con.prepareStatement("delete from adult_songs_playlist where plid=?");
                ps.setInt(1, selectedplayListId);
                ps.executeUpdate();
                ps = con.prepareStatement("delete from child_podcast_playlist where plid=?");
                ps.setInt(1, selectedplayListId);
                ps.executeUpdate();
                ps = con.prepareStatement("delete from adult_podcast_playlist where plid=?");
                ps.setInt(1, selectedplayListId);
                ps.executeUpdate();
                ps = con.prepareStatement("delete from playlist  where plid=?");
                ps.setInt(1, selectedplayListId);
                ps.executeUpdate();
                ps.setInt(1, selectedplayListId);
                ps.executeUpdate();
                System.out.println("PLAYLIST IS SUCCESSFULLY DELETED");}
            else
                System.out.println("PLAYLIST DOES NOT EXIST");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void displayPlayList(int loginId) {
        int plid = 0, count = 1, selectedplayListId = 0, operations;
        String playListName, createdOn;
        Scanner sc = new Scanner(System.in);
        List<Integer> playListIds = new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps = con.prepareStatement("select * from playlist where lid=?");
            ps.setInt(1, loginId);
            ResultSet rs = ps.executeQuery();
            if (rs.next() == false) {
                System.out.println("NO PLAYLISTS ARE CREATED TILL NOW.");
                return;
            } else {
                do {
                    if (count == 1) {
                        System.out.format("%30s %30s %30s", "PlayList Id", "PlayList Name", "Created On");
                        System.out.println();
                        System.out.println("           __________________________________________________________________________________________________");
                        count++;
                    }
                    plid = (rs.getInt(1));
                    playListName = rs.getString(2);
                    createdOn = rs.getString(3);
                    System.out.format("%30d %30s %30s", plid, playListName, createdOn);
                    System.out.println();
                    playListIds.add(plid);
                } while (rs.next());
            }
            System.out.println("ENTER PLAYLIST ID TO PERFORM OPERATIONS");
            selectedplayListId = sc.nextInt();
            if(playListIds.contains(selectedplayListId)) {
                while (true) {
                    System.out.println("ENTER : 1- TO PERFORM OPERATIONS ON PLAYLIST SONGS OR PODCASTS. 2- EXIT. ");
                    operations = sc.nextInt();
                    if (operations == 1) {
                        this.operations(selectedplayListId, loginId);
                        break;
                    } else if (operations == 2) {
                        break;
                    } else {
                        System.out.println("INVALID INPUT");
                    }
                }
            }
            else
                System.out.println("PLAYLIST DOES NOT EXIST ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void operations(int playListId, int loginId) {
        int addOrDeleteOrPlaySongsOrPodcasts;
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Enter 1 -FOR ADDING SONGS   2 - FOR ADDING PODCASTS   3- FOR DELETING SONGS   4-FOR  DELETING PODCASTS   5-FOR PLAYING SONGS   6-FOR PLAYING PODCAST");
            addOrDeleteOrPlaySongsOrPodcasts = sc.nextInt();
            if (addOrDeleteOrPlaySongsOrPodcasts == 1 || addOrDeleteOrPlaySongsOrPodcasts == 2 || addOrDeleteOrPlaySongsOrPodcasts == 3 || addOrDeleteOrPlaySongsOrPodcasts == 4 || addOrDeleteOrPlaySongsOrPodcasts == 5 || addOrDeleteOrPlaySongsOrPodcasts == 6) {
                break;
            } else {
                System.out.println("INVALID OPTION");
            }
        }
        if (addOrDeleteOrPlaySongsOrPodcasts == 1)
            this.addSong(loginId, playListId);
        else if (addOrDeleteOrPlaySongsOrPodcasts == 2)
            this.addPodcast(loginId, playListId);
        else if (addOrDeleteOrPlaySongsOrPodcasts == 3)
              this.deleteSong(playListId);
        else if (addOrDeleteOrPlaySongsOrPodcasts == 4)
              this.deletePodcast(playListId);
        else if (addOrDeleteOrPlaySongsOrPodcasts == 5)
            this.playSongFromSongsPlayList(loginId, playListId);
        else
            this.playPodcastFromPlaylist(loginId, playListId);
    }


    public void addSong(int loginId, int playListId) {
        Scanner sc = new Scanner(System.in);
        int option = 0, songId = 0, count = 1, selectedSongId;
        String key, songName;
        List<Integer> songIds =new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            while (true) {
                System.out.println("ENTER APPROPRIATE OPTION FOR ADDING SONGS. 1 -FOR CHILD SONGS.  2 -FOR CHILD RHYMES. 3 -ADULT SONGS    4 -EXIT");
                option = sc.nextInt();
                if (option == 1) {
                    ps = con.prepareStatement("select  distinct sid,sname from child_song where sname like ? ");

                } else if (option == 2) {
                    ps = con.prepareStatement("select  distinct sid,sname from child_rhymes where sname like ? ");

                } else if (option == 3) {
                    ps = con.prepareStatement("select  distinct sid,sname from adult_song where sname like ?");

                } else if (option == 4) {
                    break;
                } else {
                    System.out.println("INVALID OPTION");
                    continue;
                }
                System.out.println("ENTER NAME OR ANY SUBNAMES OF SONG");
                key = sc.next();

                ps.setString(1, "%" + key + "%");
                rs = ps.executeQuery();
                if (rs.next() == false) {
                    System.out.println("NO SONG FOUND");
                    continue;
                } else {
                    do {
                        songId = rs.getInt(1);
                        songName = rs.getString(2);
                        if (count == 1) {
                            System.out.format("%30s %40s", "SONG ID", "SONG NAME");
                            System.out.println();
                            System.out.println("          _________________________________________________________________________");
                            count++;
                        }
                        System.out.format("%30d %40s", songId, songName);
                        System.out.println();
                        songIds.add(songId);
                    } while (rs.next());
                    count = 1;
                }
                System.out.println("ENTER SONG ID");
                selectedSongId = sc.nextInt();
                if(songIds.contains(selectedSongId)){
                    if (option == 1) {
                        ps = con.prepareStatement("insert into child_songs_playlist(plid,sid) values(?,?)");

                    } else if (option == 2) {
                        ps = con.prepareStatement("insert into child_rhymes_playlist(plid,sid) values(?,?)");

                    } else {
                        ps = con.prepareStatement("insert into adult_songs_playlist(plid,sid) values(?,?)");
                    }
                    ps.setInt(1, playListId);
                    ps.setInt(2, selectedSongId);
                    int k = ps.executeUpdate();
                    if (k > 0)
                        System.out.println("SUCCESSFULLY ADDED TO PLAYLIST");

            }else System.out.println("SONG DOES NOT EXISTS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addPodcast(int loginId, int playListId) {
        Scanner sc = new Scanner(System.in);
        int option = 0, podId = 0, count = 1, selectedPodId;
        String key, podName;
        List<Integer> podIds=new ArrayList<>();
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            while (true) {
                System.out.println("ENTER APPROPRIATE OPTION FOR ADDING PODCAST. 1 -FOR CHILD PODCAST.  2 -ADULT PODCAST.    3 -EXIT");
                option = sc.nextInt();
                if (option == 1) {
                    ps = con.prepareStatement("select  distinct podid,podname from child_podcast where podname like ? ");

                } else if (option == 2) {
                    ps = con.prepareStatement("select  distinct podid,podname from adult_podcast where podname like ?");

                } else if (option == 3) {
                    break;
                } else {
                    System.out.println("INVALID OPTION");
                    continue;
                }
                System.out.println("ENTER NAME OR ANY SUBNAMES OF PODCAST");
                key = sc.next();

                ps.setString(1, "%" + key + "%");
                rs = ps.executeQuery();
                if (rs.next() == false) {
                    System.out.println("NO PODCAST FOUND");
                    continue;
                } else {
                    do {
                        podId = rs.getInt(1);
                        podName = rs.getString(2);
                        if (count == 1) {
                            System.out.format("%30s %40s", "PODCAST ID", "PODCAST NAME");
                            System.out.println();
                            System.out.println("          _________________________________________________________________________");
                            count++;
                        }
                        System.out.format("%30d %40s", podId, podName);
                        System.out.println();
                        podIds.add(podId);
                    } while (rs.next());
                    count = 1;
                }
                System.out.println("ENTER PODCAST ID");
                selectedPodId = sc.nextInt();
                if(podIds.contains(selectedPodId)){
                if (option == 1) {
                    ps = con.prepareStatement("insert into child_podcast_playlist(plid,podid) values(?,?)");
                } else {
                    ps = con.prepareStatement("insert into adult_podcast_playlist(plid,podid) values(?,?)");
                }
                ps.setInt(1, playListId);
                ps.setInt(2, selectedPodId);
                int k = ps.executeUpdate();
                if (k > 0)
                    System.out.println("SUCCESSFULLY ADDED TO PLAYLIST");

            }else System.out.println("PODCAST DOESNOT EXIST");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void playSongFromSongsPlayList(int loginId, int playListId) {
        try {
            List<String> links = new ArrayList<>();
            List<String> descs = new ArrayList<>();
            List<String> songNames = new ArrayList<>();
            List<String> albumNames = new ArrayList<>();
            List<String> genres = new ArrayList<>();
            List<Integer> songIds = new ArrayList<>();
            int songORRhymes, songId, selectedSongIndex, flag = 1;
            String songName, genreName, descOfSong, link, albumName;
            Scanner sc = new Scanner(System.in);
            List<Integer> playListIds = new ArrayList<>();
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            while (true) {
                System.out.println("ENTER : 1-CHILD SONGS 2-CHILD RHYMES 3-ADULT SONGS 4-EXIT");
                songORRhymes = sc.nextInt();
                flag=1;
                if (songORRhymes == 1) {
                    ps = con.prepareStatement("select distinct s.sid,s.sname,g.gname,a.alname,s.descriptionofSong,s.link from playlist p join child_songs_playlist c on p.plid=c.plid join child_song s on  s.sid= c.sid join genre g on g.gid=s.gid join child_album a on a.alid=s.alid where p.plid=?");
                } else if (songORRhymes == 2) {
                    ps = con.prepareStatement("select distinct s.sid,s.sname,g.gname,a.alname,s.descriptionofSong,s.link from playlist p join child_rhymes_playlist c on p.plid=c.plid join child_rhymes s on  s.sid= c.sid join genre g on g.gid=s.gid join child_album a on a.alid=s.alid where p.plid=?");
                } else if (songORRhymes == 3) {
                    ps = con.prepareStatement("select distinct s.sid,s.sname,g.gname,a.alname,s.descriptionofSong,s.link from playlist p join adult_songs_playlist c on p.plid=c.plid join adult_song s on  s.sid= c.sid join genre g on g.gid=s.gid join adult_album a on a.alid=s.alid where p.plid=?");
                } else if (songORRhymes == 4) {
                    break;
                } else {
                    System.out.println("INVALID OPTION");
                    continue;
                }
                ps.setInt(1,playListId);
                rs = ps.executeQuery();
                if (rs.next() == false) {
                    System.out.println("NO SONGS FOUND");
                    return;
                } else {
                    System.out.format("%30s %30s %30s %30s", "SONG ID", "SONG NAME", "GENRE NAME", "ALBUM NAME");
                    System.out.println();
                    System.out.println("               ______________________________________________________________________________________________________________________________");
                    do {
                        songId = rs.getInt(1);
                        songIds.add(songId);
                        songName = rs.getString(2);
                        songNames.add(songName);
                        genreName = rs.getString(3);
                        genres.add(genreName);
                        albumName = rs.getString(4);
                        albumNames.add(albumName);
                        descOfSong = rs.getString(5);
                        descs.add(descOfSong);
                        link = rs.getString(6);
                        links.add(link);
                        System.out.format("%30d %30s %30s %30s", flag, songName, genreName, albumName);
                        System.out.println();
                        flag++;
                    } while (rs.next());
                    System.out.println("ENTER THE SONG ID TO PLAY");
                    selectedSongIndex = sc.nextInt();
                    if(songIds.contains(selectedSongIndex)){
                    System.out.println("SONG NAME :" + songNames.get(selectedSongIndex - 1) + "\nALBUM NAME :" + albumNames.get(selectedSongIndex - 1));
                    System.out.println("GENRE :" + genres.get(selectedSongIndex - 1));
                    System.out.println("DESCRIPTION :" + descs.get(selectedSongIndex - 1));
                    AudioPlayer.playMusic(links.get(selectedSongIndex - 1));
                }else System.out.println("SONG DOESNOT EXISTS");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void playPodcastFromPlaylist(int loginId, int playListId) {
        List<Integer> podId = new ArrayList<>();
        List<Integer> epiId = new ArrayList<>();
        List<String> epiNames = new ArrayList<>();
        List<String> descofepis = new ArrayList<>();
        List<String> links = new ArrayList<>();
        List<String> epigenres = new ArrayList<>();
        List<String> onAirDates = new ArrayList<>();
        int epid, selectedEpisodeId, dayDiff, childOrAdultPodcast;
        String episodename, onAirDate = "", descriptionofepisode, gname, link = "", podcastName;
        Scanner sc = new Scanner(System.in);
        int podcastId, selectedPodcastId, podid;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            while (true) {
                System.out.println("ENTER : 1-CHILD PODCAST    2-ADULT PODCAST    3-EXIT");
                childOrAdultPodcast = sc.nextInt();
                if (childOrAdultPodcast == 1) {
                    ps = con.prepareStatement("select distinct cp.podid,cp.podname from child_podcast_playlist cpp join child_podcast cp on cp.podid=cpp.podid where plid=?");
                } else if (childOrAdultPodcast == 2) {
                    ps = con.prepareStatement("select distinct cp.podid,cp.podname from adult_podcast_playlist cpp join adult_podcast cp on cp.podid=cpp.podid where plid=?");
                } else if (childOrAdultPodcast == 3) {
                    break;
                } else {
                    System.out.println("INVALID OPTION");
                    continue;
                }
                ps.setInt(1, playListId);
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
                if(podId.contains(selectedPodcastId)){
                ps = con.prepareStatement("select podname from adult_podcast where podid=?");
                ps.setInt(1, selectedPodcastId);
                rs = ps.executeQuery();
                rs.next();
                podcastName = rs.getString(1);
                if (childOrAdultPodcast == 1) {
                    ps = con.prepareStatement("select distinct epid,episodename,onAirDate,descriptionofepisode,gname,link,epitime from child_podcast_episodes cpe join genre on genre.gid=cpe.gid where cpe.podid=?");
                } else {
                    ps = con.prepareStatement("select distinct epid,episodename,onAirDate,descriptionofepisode,gname,link,epitime from adult_podcast_episodes cpe join genre on genre.gid=cpe.gid where cpe.podid=?");
                }
                ps.setInt(1, selectedPodcastId);
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
                    epigenres.add(gname);
                    links.add(link);
                }
                System.out.print("\nENTER EPISODE ID :");
                selectedEpisodeId = sc.nextInt();
                if(epiId.contains(selectedEpisodeId)){
                ps = con.prepareStatement("SELECT DATEDIFF(?,now()) AS DateDiff");
                ps.setString(1, onAirDate);
                rs = ps.executeQuery();
                rs.next();
                dayDiff = rs.getInt(1);
                if (dayDiff <= 0) {
                    System.out.println("EPISODE NAME :" + epiNames.get(selectedEpisodeId - 1) + "\nPODCAST NAME : " + podcastName);
                    System.out.println("GENRE :" + epigenres.get(selectedEpisodeId - 1));
                    System.out.println("DESCRIPTION :" + descofepis.get(selectedEpisodeId - 1));

                    AudioPlayer.playMusic(links.get(selectedEpisodeId - 1));
                } else {
                    System.out.println("THIS EPISODE IS NOT YET RELEASED.WILL BE RELEASED IN " + dayDiff + " DAYS. RELEASE DATE - " + onAirDates.get(selectedEpisodeId - 1));
                }} else System.out.println("EPISODE DOESNOT EXISTS.");
            }else System.out.println("PODCAST DOESNOT EXISTS");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSong(int playListId) {
        try {
            Scanner sc = new Scanner(System.in);
            int option,songId;
            List<Integer> songIds=new ArrayList<>();

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            while (true) {
                System.out.println("ENTER APPROPRIATE OPTION FOR DELETING SONGS. 1 -FOR CHILD SONGS.  2 -FOR CHILD RHYMES. 3 -ADULT SONGS    4 -EXIT");
                option = sc.nextInt();
                if (option == 1) {
                    ps = con.prepareStatement("select  distinct csp.sid,sname from child_songs_playlist csp join child_song cs on cs.sid=csp.sid where plid=? ");

                } else if (option == 2) {
                    ps = con.prepareStatement("select  distinct csp.sid,sname from child_rhymes_playlist csp join child_rhymes cs on cs.sid=csp.sid where plid=?");

                } else if (option == 3) {
                    ps = con.prepareStatement("select  distinct csp.sid,sname from adult_songs_playlist csp join adult_song cs on cs.sid=csp.sid where plid=?");

                } else if (option == 4) {
                    break;
                } else {
                    System.out.println("INVALID OPTION");
                    continue;
                }
                ps.setInt(1, playListId);
                rs = ps.executeQuery();
                if (rs.next() == false) {
                    System.out.println("NO SONGS FOUND");
                    return;
                } else {
                    System.out.format("%30s %40s", "SONG ID", "SONG NAME");
                    System.out.println();
                    System.out.println("               _______________________________________________________________________");
                    do {
                        songIds.add(rs.getInt(1));
                        System.out.format("%30d %40s", rs.getInt(1), rs.getString(2));
                        System.out.println();
                    } while (rs.next());
                    System.out.println("ENTER SONG ID");
                    songId = sc.nextInt();
                    if(songIds.contains(songId)){
                    if (option == 1) {
                        ps = con.prepareStatement("delete from child_songs_playlist where plid=? and sid=?");
                    } else if (option == 2) {
                        ps = con.prepareStatement("delete from child_rhymes_playlist where plid=? and sid=?");
                    } else{
                        ps = con.prepareStatement("delete from adult_songs_playlist where plid=? and sid=?");
                    }
                    ps.setInt(1, playListId);
                    ps.setInt(2, songId);
                    int k = ps.executeUpdate();
                    if (k > 0)
                        System.out.println("SONG IS DELETED SUCCESFULLY");
                }else System.out.println("SONG DOESNOT EXISTS");
                }
            }

           }catch(SQLException e){e.printStackTrace();}
    }
    public void deletePodcast(int playListId){
        int option,podcastId;
        List<Integer> podcastIds=new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jukebox", "root", "password@123");
            PreparedStatement ps;
            ResultSet rs;
            while (true) {
                System.out.println("ENTER APPROPRIATE OPTION FOR DELETING PODCAST. 1 -FOR CHILD PODCAST.  2 -ADULT PODCAST    3 -EXIT");
                option = sc.nextInt();
                if (option == 1) {
                    ps = con.prepareStatement("select  distinct csp.podid,podname from child_podcast_playlist csp join child_podcast cs on cs.podid=csp.podid where plid=? ");
                } else if (option == 2) {
                    ps = con.prepareStatement("select  distinct csp.podid,podname from adult_podcast_playlist csp join adult_podcast cs on cs.podid=csp.podid where plid=? ");

                } else if (option == 3) {
                    break;
                } else {
                    System.out.println("INVALID OPTION");
                    continue;
                }
                ps.setInt(1, playListId);
                rs = ps.executeQuery();
                if (rs.next() == false) {
                    System.out.println("NO PODCASTS FOUND");
                    return;
                }
                else {
                    System.out.format("%30s %40s", "PODCAST ID", "PODCAST NAME");
                    System.out.println();
                    System.out.println("               _______________________________________________________________________");
                    do {
                        podcastIds.add(rs.getInt(1));
                        System.out.format("%30d %40s", rs.getInt(1), rs.getString(2));
                        System.out.println();
                    } while (rs.next());
                    System.out.println("ENTER PODCAST ID");
                    podcastId = sc.nextInt();
                    if(podcastIds.contains(podcastId)){
                    if (option == 1) {
                        ps = con.prepareStatement("delete from child_podcast_playlist where plid=? and podid=?");
                    } else{
                        ps = con.prepareStatement("delete from adult_podcast_playlist where plid=? and podid=?");
                    }
                    ps.setInt(1, playListId);
                    ps.setInt(2,podcastId);
                    int k = ps.executeUpdate();
                    if (k > 0)
                        System.out.println("PODCAST IS DELETED SUCCESFULLY");
                }else System.out.println("PODCAST DOESNOT EXISTS");
            }
            }
        }catch(SQLException e){e.printStackTrace();}

    }
}


