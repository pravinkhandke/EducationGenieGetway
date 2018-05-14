import { element, by } from 'protractor';

export class GradeComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-grade div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class GradeUpdatePage {
    PageTitle = element(by.css('h2#jhi-grade-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    gradeInput = element(by.css('input#field_grade'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setGradeInput(grade) {
        this.gradeInput.sendKeys(grade);
    }

    getGradeInput() {
        return this.gradeInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    cancel() {
        this.cancelButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
