import { element, by } from 'protractor';

export class ScoreComponentsPage {
    createButton = element(by.css('#jh-create-entity'));
    title = element.all(by.css('jhi-score div h2#page-heading span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class ScoreUpdatePage {
    PageTitle = element(by.css('h2#jhi-score-heading'));
    saveButton = element(by.css('#save-entity'));
    cancelButton = element(by.css('#cancel-save'));
    scoreInput = element(by.css('input#field_score'));
    userInfoSelect = element(by.css('select#field_userInfo'));

    getPageTitle() {
        return this.PageTitle.getAttribute('jhiTranslate');
    }

    setScoreInput(score) {
        this.scoreInput.sendKeys(score);
    }

    getScoreInput() {
        return this.scoreInput.getAttribute('value');
    }

    userInfoSelectLastOption() {
        this.userInfoSelect
            .all(by.tagName('option'))
            .last()
            .click();
    }

    userInfoSelectOption(option) {
        this.userInfoSelect.sendKeys(option);
    }

    getUserInfoSelect() {
        return this.userInfoSelect;
    }

    getUserInfoSelectedOption() {
        return this.userInfoSelect.element(by.css('option:checked')).getText();
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
