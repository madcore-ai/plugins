pipelineJob('madcore.plugin.cuda.deploy') {
    parameters {
        stringParam('REPO_URL', 'https://github.com/madcore-ai/containers', '')
	    stringParam('APP_NAME', 'cuda', '')
	    stringParam('DOCKERFILE_PATH', 'cuda', 'Specify path to docker file relative to root repo.')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Docker: build container'
                build job: 'madcore.docker.image.build', parameters: [string(name: 'REPO_URL', value: params.REPO_URL), string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'DOCKERFILE_PATH', value: params.DOCKERFILE_PATH)]
                stage 'Docker: registry publish'
                build job: 'madcore.docker.registry.publish', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Docker: registry status'
                build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Helm: install'
                build job: 'madcore.helm.install', parameters: [string(name: 'CHART', value: params.APP_NAME + '/chart'), string(name: 'RELEASE_NAME', value: params.APP_NAME), string(name: 'NAMESPACE', value: params.APP_NAME + '-plugin')]
                }
            """.stripIndent())
	    }
    }
}
