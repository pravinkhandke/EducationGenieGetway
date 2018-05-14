import { element, by } from 'protractor';

export class TopicComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-topic div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TopicUpdatePage {
    PageTitle = element(by.css('h2#jhi-topic-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    nameInput = element(by.css('input#field_name'));
    chapterSelect = element(by.css('select#field_chapter'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setNameInput(name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput() {
        return this.nameInput.getAttribute('value');
    }

    chapterSelectLastOption() {
        this.chapterSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    chapterSelectOption(option) {
        this.chapterSelect.sendKeys(option);
    }

    getChapterSelect() {
        return this.chapterSelect;
    }

    getChapterSelectedOption() {
        return this.chapterSelect.element(by.css('option:checked')).getText();
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
