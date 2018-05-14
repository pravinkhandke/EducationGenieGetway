import { browser } from 'protractor';
import { NavBarPage } from './../../page-objects/jhi-page-objects';
import { TopicComponentsPage, TopicUpdatePage } from './topic.page-object';

describe('Topic e2e test', () => {
    let navBarPage: NavBarPage;
    let topicUpdatePage: TopicUpdatePage;
    let topicComponentsPage: TopicComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Topics', () => {
        navBarPage.goToEntity('topic');
        topicComponentsPage = new TopicComponentsPage();
        expect(topicComponentsPage.getTitle()).toMatch(/educationGenieApp.topic.home.title/);
    });

    it('should load create Topic page', () => {
        topicComponentsPage.clickOnCreateButton();
        topicUpdatePage = new TopicUpdatePage();
        expect(topicUpdatePage.getPageTitle()).toMatch(/educationGenieApp.topic.home.createOrEditLabel/);
        topicUpdatePage.cancel();
    });

    it('should create and save Topics', () => {
        topicComponentsPage.clickOnCreateButton();
        topicUpdatePage.setNameInput('name');
        expect(topicUpdatePage.getNameInput()).toMatch('name');
        topicUpdatePage.chapterSelectLastOption();
        topicUpdatePage.save();
        expect(topicUpdatePage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});
