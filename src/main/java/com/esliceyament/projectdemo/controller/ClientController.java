package com.esliceyament.projectdemo.controller;

import com.esliceyament.projectdemo.dto.ClientRequest;
import com.esliceyament.projectdemo.dto.ClientResponse;
import com.esliceyament.projectdemo.service.ClientService;
import com.esliceyament.projectdemo.service.EmailSenderService;
import com.esliceyament.projectdemo.service.ExcelExportService;
import com.esliceyament.projectdemo.service.TaskExcelService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;
    private final ExcelExportService excelExportService;
    private final EmailSenderService emailSenderService;
    private final TaskExcelService taskExcelService;

    @PostMapping("/addClient")
    public void addClient(ClientRequest clientRequest) {
        clientService.addClient(clientRequest);
    }

    @GetMapping("/getAllClients")
    public List<ClientResponse> getAllClients() {
        return clientService.getAllClients();
    }

    @PostMapping("/exportClientData")
    public ResponseEntity<String> exportClientData(@RequestParam Long clientId) throws IOException {
        excelExportService.exportDataToExcel(clientId);
        return ResponseEntity.ok("Data exported successfully");
    }

    @PostMapping(value="/importClientData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importDataFromExcel(@RequestPart("file") MultipartFile file) throws IOException {
        excelExportService.importDataFromExcel(file);
        return ResponseEntity.ok("Data imported successfully");
    }

    @PostMapping(value="/sendEmail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> sendEmail(@RequestParam String toEmail, @RequestParam String subject,
                                            @RequestParam String body, @RequestPart MultipartFile file) throws MessagingException {
        emailSenderService.sendEmail(toEmail, subject, body, file);
        return ResponseEntity.ok("Email sent successfully!");
    }

    @PostMapping("/exportAllClientData")
    public ResponseEntity<String> exportAllClientData() throws IOException {
        taskExcelService.exportAllDataToExcel();
        return ResponseEntity.ok("Data exported successfully");
    }

    @PostMapping(value="/importAllClientData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> importAllDataFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        taskExcelService.importAllDataFromExcel(file);
        return ResponseEntity.ok("Data imported successfully");
    }


}