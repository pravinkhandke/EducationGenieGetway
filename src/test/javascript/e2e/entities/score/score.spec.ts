import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { ScoreComponentsPage, ScoreUpdatePage } from './score.page-object';

describe('Score e2e test', () => {
    let navBarPage: NavBarPage;
    let scoreUpdatePage: ScoreUpdatePage;
    let scoreComponentsPage: ScoreComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Scores', () => {
        navBarPage.goToEntity('score');
        scoreComponentsPage = new ScoreComponentsPage();
        expect(scoreComponentsPage.getTitle()).toMatch(/educationGenieApp.score.home.title/);
    });

    it('should load create Score page', () => {
        scoreComponentsPage.clickOnCreateButton();
        scoreUpdatePage = new ScoreUpdatePage();
        expect(scoreUpdatePage.getPageTitle()).toMatch(/educationGenieApp.score.home.createOrEditLabel/);
        scoreUpdatePage.cancel();
    });

    it('should create and save Scores', () => {
        scoreComponentsPage.clickOnCreateButton();
        scoreUpdatePage.setScoreInput('5');
        expect(scoreUpdatePage.getScoreInput()).toMatch('5');
        scoreUpdatePage.userInfoSelectLastOption();
        scoreUpdatePage.save();
        expect(scoreUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
