/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Datnt
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Match {
    public int Id;
    public int HomeTeamId;   
    public int AwayTeamId;
    public int LeaugeId;
    public String Name;
    public String Address;
    public Date StartAt;
    public Date EndAt;
    public int TeamSize;
    public Date UpdateAt;
}