import dto.OperatorDto;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Main {
    private static Map<Integer, List<OperatorDto>> data;

    static {
        try(FileInputStream file = new FileInputStream(new File("src/main/resources/DEF-ALL.xlsx"))) {
            data = new HashMap<>();
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
        }
        catch (Exception e) {
            System.out.println("Не удалось открыть файл");
        }
    }

    public static String getOperator(String number){
        String answer = null;
        long startTime = System.nanoTime();

        try {
            Integer userCode = Integer.parseInt(number.substring(1, 4));
            int userNumber = Integer.parseInt(number.substring(4));

            List<OperatorDto> searchList = data.get(userCode);
            if (searchList == null) {
                return null;
            }
            int start = 0;
            int end = searchList.size() - 1;
            while (start <= end) {
                int mid = (start + end) / 2;
                int midStartNumber = searchList.get(mid).getNumber();
                int nextStartNumber = mid + 1 < searchList.size() ? searchList.get(mid + 1).getNumber() : Integer.MAX_VALUE;

                if (userNumber >= midStartNumber && userNumber < nextStartNumber) {
                    if (userNumber > midStartNumber + searchList.get(mid).getCapacity()) {
                        return null;
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
            return answer;
        }
        catch (Exception e){
            return null;
        }
    }

    public static void main(String[] args){
            String number = "";
            System.out.println("Введите: \"exit\", чтобы выйти");
            while(!number.equals("exit")) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите номер в формате:7*");
                number = scanner.nextLine();
                System.out.println(getOperator(number));
            }
    }
}
