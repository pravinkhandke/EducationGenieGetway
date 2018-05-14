import { element, by } from 'protractor';

export class ChapterComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-chapter div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ChapterUpdatePage {
    PageTitle = element(by.css('h2#jhi-chapter-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    nameInput = element(by.css('input#field_name'));
    subjectSelect = element(by.css('select#field_subject'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setNameInput(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    subjectSelectLastOption() {
        this.subjectSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    subjectSelectOption(option) {
        this.subjectSelect.sendKeys(option);
    }

    getSubjectSelect() {
        return this.subjectSelect;
    }

    getSubjectSelectedOption() {
        return this.subjectSelect.element(by.css('option:checked')).getText();
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
