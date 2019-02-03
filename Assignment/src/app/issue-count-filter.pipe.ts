import { Pipe, PipeTransform } from '@angular/core';
import { Customer } from './customer';

@Pipe({
  name: 'issueCountFilter'
})
export class IssueCountFilterPipe implements PipeTransform {

  transform(items: Customer[], minIssueCount?: any): any[] {
    if (!items) {
      return [];
    }
    if (!minIssueCount) {
      return items;
    }
    return items.filter( item => {
      return item.issueCount < minIssueCount;
    });
  }

}
