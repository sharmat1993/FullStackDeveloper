import { Component } from '@angular/core';
import { Customer } from './customer';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  customer: Customer;
  customers: Array<Customer> = new Array<Customer>();
  csvFileColumnHeaders: string[];
  csvFileContent: string;

  changeListener(files: FileList): void {
    if (files && files.length > 0) {
      const file: File = files.item(0);
      const fileReader: FileReader = new FileReader();
      fileReader.readAsText(file);
      fileReader.onload = (e) => {
        this.csvFileContent = fileReader.result;
      };
    }
  }

  uploadFile(): void {
    const csvSeparator = ',';
    const lineSeparator = '\n';
    const csvFilelines = this.csvFileContent.split(lineSeparator);
    this.csvFileColumnHeaders = csvFilelines[0].replace(/\"/g, '').split(csvSeparator);
    csvFilelines.splice(0, 1);

    csvFilelines.forEach(csvFileline => {
      csvFileline = csvFileline.replace(/\"/g, '');
      const csvFilecolumns: string[] = csvFileline.split(csvSeparator);
      this.customer = new Customer();
      this.customer.firstName = csvFilecolumns[0];
      this.customer.surName = csvFilecolumns[1];
      this.customer.issueCount = Number(csvFilecolumns[2]);
      this.customer.dateOfBirth = csvFilecolumns[3];
      this.customers.push(this.customer);
    });
  }
}
