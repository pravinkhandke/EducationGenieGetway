import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { GradeComponentsPage, GradeUpdatePage } from './grade.page-object';

describe('Grade e2e test', () => {
    let navBarPage: NavBarPage;
    let gradeUpdatePage: GradeUpdatePage;
    let gradeComponentsPage: GradeComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Grades', () => {
        navBarPage.goToEntity('grade');
        gradeComponentsPage = new GradeComponentsPage();
        expect(gradeComponentsPage.getTitle()).toMatch(/educationGenieApp.grade.home.title/);
    });

    it('should load create Grade page', () => {
        gradeComponentsPage.clickOnCreateButton();
        gradeUpdatePage = new GradeUpdatePage();
        expect(gradeUpdatePage.getPageTitle()).toMatch(/educationGenieApp.grade.home.createOrEditLabel/);
        gradeUpdatePage.cancel();
    });

    it('should create and save Grades', () => {
        gradeComponentsPage.clickOnCreateButton();
        gradeUpdatePage.setGradeInput('5');
        expect(gradeUpdatePage.getGradeInput()).toMatch('5');
        gradeUpdatePage.save();
        expect(gradeUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
