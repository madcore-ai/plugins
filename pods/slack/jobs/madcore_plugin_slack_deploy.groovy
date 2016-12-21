pipelineJob('madcore.plugin.slack.deploy') {
    parameters {
        stringParam('REPO_URL', 'https://github.com/madcore-ai/containers', '')
	    stringParam('APP_NAME', 'slack', '')
	    stringParam('SECRET', 'slack-token=NOTOKENHERE;slack-test=test', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Docker: build container'
                build job: 'madcore.docker.image.build', parameters: [string(name: 'REPO_URL', value: params.REPO_URL), string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Docker: registry publish'
                build job: 'madcore.docker.registry.publish', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Docker: registry status'
                build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Kubernetes: create'
                build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'slack/kub')]
                stage 'Kubernetes: create secret'
                build job: 'madcore.kubectl.create.secret', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SECRET', value: params.SECRET)]
                }
            """.stripIndent())
	    }
    }
}
