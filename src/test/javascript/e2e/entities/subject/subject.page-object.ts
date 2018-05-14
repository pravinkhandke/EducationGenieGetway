import { element, by } from 'protractor';

export class SubjectComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-subject div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class SubjectUpdatePage {
    PageTitle = element(by.css('h2#jhi-subject-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    nameInput = element(by.css('input#field_name'));
    gradeSelect = element(by.css('select#field_grade'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setNameInput(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    gradeSelectLastOption() {
        this.gradeSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    gradeSelectOption(option) {
        this.gradeSelect.sendKeys(option);
    }

    getGradeSelect() {
        return this.gradeSelect;
    }

    getGradeSelectedOption() {
        return this.gradeSelect.element(by.css('option:checked')).getText();
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
