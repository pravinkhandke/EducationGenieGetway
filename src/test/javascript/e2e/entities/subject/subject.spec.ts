import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { SubjectComponentsPage, SubjectUpdatePage } from './subject.page-object';

describe('Subject e2e test', () => {
    let navBarPage: NavBarPage;
    let subjectUpdatePage: SubjectUpdatePage;
    let subjectComponentsPage: SubjectComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Subjects', () => {
        navBarPage.goToEntity('subject');
        subjectComponentsPage = new SubjectComponentsPage();
        expect(subjectComponentsPage.getTitle()).toMatch(/educationGenieApp.subject.home.title/);
    });

    it('should load create Subject page', () => {
        subjectComponentsPage.clickOnCreateButton();
        subjectUpdatePage = new SubjectUpdatePage();
        expect(subjectUpdatePage.getPageTitle()).toMatch(/educationGenieApp.subject.home.createOrEditLabel/);
        subjectUpdatePage.cancel();
    });

    it('should create and save Subjects', () => {
        subjectComponentsPage.clickOnCreateButton();
        subjectUpdatePage.setNameInput('name');
        expect(subjectUpdatePage.getNameInput()).toMatch('name');
        subjectUpdatePage.gradeSelectLastOption();
        subjectUpdatePage.save();
        expect(subjectUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
