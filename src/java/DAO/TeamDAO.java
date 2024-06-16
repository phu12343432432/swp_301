/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAL.DBContext;
import Model.Team;
import Model.User;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ADMIN
 */
public class TeamDAO extends DBContext {

    private Connection con;
    private List<User> user;
    PreparedStatement ps;
    ResultSet rs;

    public TeamDAO() {
        try {
            con = new DBContext().getConnection();
            System.out.println("Connect success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public Team getTeamById(int _teamId) {
        try {
            String sql = "SELECT * FROM Team WHERE Id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, _teamId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Team _team = new Team();
                int teamId = rs.getInt("Id");
                String Name = rs.getString("Name");
                String ShortName = rs.getString("ShortName");
                String Description = rs.getString("Description");
                int teamSize = rs.getInt("TeamSize");
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);

                _team.setImage(base64Image);
                _team.setName(Name);
                _team.setShortName(ShortName);
                _team.setDescription(Description);
                _team.setTeamSize(teamSize);
                _team.setId(teamId);
                return _team;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public Team getTeamByUserId(int userId) {
        try {
            String sql = "SELECT * FROM Team WHERE UserId = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Team _team = new Team();
                int teamId = rs.getInt("Id");
                String Name = rs.getString("Name");
                String ShortName = rs.getString("ShortName");
                String Description = rs.getString("Description");
                int teamSize = rs.getInt("TeamSize");
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);

                _team.setImage(base64Image);
                _team.setName(Name);
                _team.setShortName(ShortName);
                _team.setDescription(Description);
                _team.setTeamSize(teamSize);
                _team.setId(teamId);
                return _team;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   
    public Team updateTeam(Team team, Part image) {
        try {
            Team _team = new Team();
            String sql = "";
            if (image != null) {
                sql = "UPDATE dbo.[Team] SET [Name] = ?, [ShortName] = ?, [TeamSize] = ?, "
                        + "[Description] = ?, UpdateAt = ?, Image = ? "
                        + "WHERE [Id] = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, team.getName());
                ps.setString(2, team.getShortName());
                ps.setInt(3, team.getTeamSize());
                ps.setString(4, team.getDescription());
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String currentDate = dateFormat.format(date);
                ps.setString(5, currentDate);
                // input steam. => Chuyen nah nay thanh ma binary.
                InputStream fileContent = image.getInputStream();
                ps.setBinaryStream(6, fileContent, (int) image.getSize());
                ps.setInt(7, team.getId());
            } else {
                sql = "UPDATE dbo.[Team] SET [Name] = ?, [ShortName] = ?, [TeamSize] = ?, "
                        + "[Description] = ?, UpdateAt = ? "
                        + "WHERE [Id] = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, team.getName());
                ps.setString(2, team.getShortName());
                ps.setInt(3, team.getTeamSize());
                ps.setString(4, team.getDescription());
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                String currentDate = dateFormat.format(date);
                ps.setString(5, currentDate);
                ps.setInt(6, team.getId());
            }

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                sql = "SELECT * FROM dbo.[Team] WHERE Id = ?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, team.getId());
                rs = ps.executeQuery();
                if (rs.next()) {
                    int teamId = rs.getInt("Id");
                    String Name = rs.getString("Name");
                    String ShortName = rs.getString("ShortName");
                    String Description = rs.getString("Description");  
                    int teamSize = rs.getInt("TeamSize");

                    byte[] imgData = rs.getBytes("Image");
                    String base64Image = Base64.getEncoder().encodeToString(imgData);

                    _team.setImage(base64Image);
                    _team.setName(Name);
                    _team.setShortName(ShortName);
                    _team.setDescription(Description);
                    _team.setTeamSize(teamSize);
                    _team.setId(teamId);
                    return _team;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean createTeam(Team team, Part image) {
        try {

            String sql = "INSERT  dbo.[Team] ([Name], [ShortName], [TeamSize], "
                    + "[Description], [CreateAt], [Image], [UserId], [IsActive]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, team.getName());
            ps.setString(2, team.getShortName());
            ps.setInt(3, team.getTeamSize());
            ps.setString(4, team.getDescription());
            LocalDateTime now = LocalDateTime.now();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(5, currentDate);
            InputStream fileContent = image.getInputStream();
            ps.setBinaryStream(6, fileContent, (int) image.getSize());
            ps.setInt(7, team.getUserId());
            ps.setBoolean(8, true);

            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
