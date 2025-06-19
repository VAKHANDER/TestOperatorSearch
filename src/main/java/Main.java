import dto.OperatorDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Main {
    public static void main(String[] args){
        try(FileInputStream file = new FileInputStream(new File("src/main/resources/DEF-ALL.xlsx"))){
            Map<Integer, List<OperatorDto>> data = new HashMap<>();
            String answer = null;

            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);

            for(Row row : sheet){
                Integer codeCell = (int) row.getCell(0).getNumericCellValue();
                OperatorDto operatorDto = new OperatorDto((int) row.getCell(1).getNumericCellValue(),
                    row.getCell(2).toString(),
                    (int) row.getCell(3).getNumericCellValue());

                List<OperatorDto> codeData = new ArrayList<>();

                if(data.containsKey(codeCell)){
                    codeData = data.get(codeCell);
                    codeData.add(operatorDto);
                    continue;
                }

                codeData.add(operatorDto);
                data.put(codeCell, codeData);
            }

            long startTime = System.nanoTime();

            Integer userCode = Integer.parseInt(args[0].substring(1, 4));
            int userNumber = Integer.parseInt(args[0].substring(4));

            List<OperatorDto> searchList = data.get(userCode);
            if (searchList == null){
                throw new RuntimeException("Данного номера не существует");
            }
            int start = 0;
            int end = searchList.size() - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                int midStartNumber = searchList.get(mid).getNumber();
                int nextStartNumber = mid + 1 < searchList.size() ? searchList.get(mid + 1).getNumber() : Integer.MAX_VALUE;

                if (userNumber >= midStartNumber && userNumber < nextStartNumber) {
                    if(userNumber > midStartNumber +  searchList.get(mid).getCapacity()){
                        throw new RuntimeException("Данного номера не существует");
                    }
                    answer = searchList.get(mid).getOperator();
                    break;
                } else if (userNumber < midStartNumber) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            }

            long endTime = System.nanoTime();
            System.out.println(endTime - startTime + " ns");
            if (answer != null) {
                System.out.println(answer);
            }
            else {
                System.out.println("Данного номера не существует");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
