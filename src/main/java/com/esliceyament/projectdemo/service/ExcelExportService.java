package com.esliceyament.projectdemo.service;

import com.esliceyament.projectdemo.dto.ClientRequest;
import com.esliceyament.projectdemo.dto.PropsRequest;
import com.esliceyament.projectdemo.model.Client;
import com.esliceyament.projectdemo.model.PropsClient;
import com.esliceyament.projectdemo.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ExcelExportService {
    private final ClientRepository clientRepository;
    private final ClientService clientService;
    private final PropsService propsService;

    private Date convertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }


    public void exportDataToExcel(Long clientId) throws IOException {
        Optional<Client> optionalClient = clientRepository.findById(clientId);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Client Data");

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Surname");
            headerRow.createCell(2).setCellValue("Email");
            headerRow.createCell(3).setCellValue("DoB");

            Date dobDate = convertLocalDateToDate(client.getDob());
            Row dataRow = sheet.createRow(1);
            dataRow.createCell(0).setCellValue(client.getName());
            dataRow.createCell(1).setCellValue(client.getSurname());
            dataRow.createCell(2).setCellValue(client.getEmail());
            Cell dobCell = dataRow.createCell(3);
            dobCell.setCellValue(dobDate);
            CellStyle cellStyle = workbook.createCellStyle();
            CreationHelper createHelper = workbook.getCreationHelper();
            cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));
            dobCell.setCellStyle(cellStyle);

            List<PropsClient> propsClients = client.getPropsClient();
            if (!propsClients.isEmpty()) {
                int colNum = 4;
                for (PropsClient propsClient : propsClients) {
                    headerRow.createCell(colNum).setCellValue(propsClient.getPropKey());
                    dataRow.createCell(colNum).setCellValue(propsClient.getValue());
                    colNum++;                }
            }

            try (FileOutputStream outputStream = new FileOutputStream("client_data.xlsx")) {
                workbook.write(outputStream);
            }
        }
    }

    public void importDataFromExcel(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();
            rows.next(); // Skip header row
            while (rows.hasNext()) {
                Row row = rows.next();
                ClientRequest clientRequest = new ClientRequest();
                clientRequest.setName(row.getCell(0).getStringCellValue());
                clientRequest.setSurname(row.getCell(1).getStringCellValue());
                clientRequest.setEmail(row.getCell(2).getStringCellValue());
                LocalDate localDate;
                Cell dateCell = row.getCell(3);

                localDate = dateCell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                clientRequest.setDob(localDate);

                Optional<Client> existingClientOptional = clientRepository.findByEmail(clientRequest.getEmail());
                Client savedClient;

                if (existingClientOptional.isPresent()) {
                    Client existingClient = existingClientOptional.get();
                    existingClient.setName(clientRequest.getName());
                    existingClient.setSurname(clientRequest.getSurname());
                    existingClient.setDob(clientRequest.getDob());
                    savedClient = clientRepository.save(existingClient);
                } else {
                    savedClient = clientService.addClient(clientRequest);
                }

                List<PropsRequest> propsRequests = new ArrayList<>();
                int cellNum = 4;
                while (cellNum < row.getLastCellNum()) {
                    PropsRequest propsRequest = new PropsRequest();
                    propsRequest.setPropKey(sheet.getRow(0).getCell(cellNum).getStringCellValue());
                    propsRequest.setValue(getCellValueAsString(row.getCell(cellNum)));
                    propsRequests.add(propsRequest);
                    cellNum++;
                }
                propsService.addProps(propsRequests, savedClient);
            }
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            default:
                return null;
        }
    }
}