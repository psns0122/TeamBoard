package jdbc.homework.boards.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Board {

   private int bno;
   private String btitle;
   private String bcontent;
   private String bwriter;
   private LocalDateTime bdate;

}
