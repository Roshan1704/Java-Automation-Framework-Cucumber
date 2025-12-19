pipeline {
    agent any
    
    tools {
        maven 'Maven 3.8.6'
        jdk 'JDK 11'
    }
    
    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser for test execution')
        choice(name: 'ENVIRONMENT', choices: ['QA', 'STAGING', 'PROD'], description: 'Select environment')
        string(name: 'TEST_SUITE', defaultValue: 'testng.xml', description: 'TestNG XML file to execute')
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
                echo "Checking out code from repository"
            }
        }
        
        stage('Build') {
            steps {
                echo "Building the project"
                sh 'mvn clean compile'
            }
        }
        
        stage('Run Tests') {
            steps {
                echo "Running tests on ${params.BROWSER} browser in ${params.ENVIRONMENT} environment"
                sh """
                    mvn clean test \
                    -Dbrowser=${params.BROWSER} \
                    -Denvironment=${params.ENVIRONMENT} \
                    -DsuiteXmlFile=${params.TEST_SUITE}
                """
            }
        }
        
        stage('Generate Allure Report') {
            steps {
                echo "Generating Allure Report"
                sh 'mvn allure:report'
            }
        }
    }
    
    post {
        always {
            echo "Archiving test results"
            junit '**/target/surefire-reports/*.xml'
            
            allure includeProperties: false,
                   jdk: '',
                   results: [[path: 'target/allure-results']]
                   
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output/reports',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report'
            ])
        }
        
        success {
            echo "Tests passed successfully!"
            emailext(
                subject: "Test Execution Success - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The test execution completed successfully.\n\nBrowser: ${params.BROWSER}\nEnvironment: ${params.ENVIRONMENT}",
                to: 'team@example.com'
            )
        }
        
        failure {
            echo "Tests failed!"
            emailext(
                subject: "Test Execution Failed - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: "The test execution failed.\n\nBrowser: ${params.BROWSER}\nEnvironment: ${params.ENVIRONMENT}\n\nCheck Jenkins console for details.",
                to: 'team@example.com'
            )
        }
    }
}
